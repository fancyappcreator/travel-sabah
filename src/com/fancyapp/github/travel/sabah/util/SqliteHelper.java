package com.fancyapp.github.travel.sabah.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.michenux.android.db.sqlite.SqlParser;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class handles sqlite related basic data access operations.
 * http://www.michenux.net/android-database-sqlite-creation-upgrade-245.html
 */
public class SqliteHelper extends SQLiteOpenHelper {
	private static final String SQL_DIR = "sql";
	private static final String UPGRADE_FILE_PREFIX = "upgrade-";
	private static final String UPGRADE_FILE_SUFFIX = ".sql";
	private final Context context;
	private final String dbName;
	private final String dbPath;
	private SQLiteDatabase infoplusDb;

	/**
	 * @param context
	 *            Application context
	 * @param name
	 *            Name of the database
	 * @param factory
	 *            Cursor factory object
	 * @param version
	 *            Version of the application
	 * @throws IOException
	 *             IO Exception
	 */
	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) throws IOException {
		super(context, name, factory, version);
		this.context = context;
		this.dbName = name;
		this.dbPath = context.getDatabasePath(dbName).getParent() + "/";

		if (loadDatabase()) {
			openDatabase();
		} else {
			createDatabase();
		}
	}

	/**
	 * @throws IOException
	 *             IO Exception
	 */
	public void createDatabase() throws IOException {
		if (loadDatabase()) {
		} else {
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException ex) {
				throw new Error("Error copying database");
			}
		}
	}

	public void deleteDatabase() {
		context.deleteDatabase(dbName);
	}

	public void refreshDatabase() {
		if (loadDatabase()) {
			context.deleteDatabase(dbName);
		}
	}

	private boolean loadDatabase() {
		boolean dbExists = false;
		try {
			final String dbFullPath = dbPath + dbName;
			final File dbFile = new File(dbFullPath);
			dbExists = dbFile.exists();
		} catch (SQLiteException ex) {
			throw new Error("Database doesn't exist");
		}
		return dbExists;
	}

	private void copyDatabase() throws IOException {
		final int bufferMaxLength = 16384;
		final InputStream inputDbStream = context.getAssets().open(
				"sql/" + dbName);
		final String outputDbPath = dbPath + dbName;
		final OutputStream outputDbStream = new FileOutputStream(outputDbPath);
		final byte[] buffer = new byte[bufferMaxLength];
		int length;
		while ((length = inputDbStream.read(buffer)) > 0) {
			outputDbStream.write(buffer, 0, length);
		}
		outputDbStream.flush();
		outputDbStream.close();
		inputDbStream.close();
	}

	/**
	 * @return SQLiteDatabase
	 * @throws SQLException
	 *             SQL Exception
	 */
	public SQLiteDatabase openDatabase() throws SQLException {
		return this.getWritableDatabase();
	}

	@Override
	public synchronized void close() {
		if (infoplusDb != null) {
			infoplusDb.close();
		}
		super.close();
	}

	/**
	 * This method loops through all the files inside assets's SQL path in
	 * search of SQL upgrade files, then proceeds to execute the upgrade files
	 * found if upgrade file's version is greater than db old version and equals
	 * or less than db new version.(This has not been tested yet) {@inheritDoc}
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			// update app's upgrade flag
			// PreferenceUtil.setUpgrade(this.context, true);

			final String[] sqlFiles = AssetHelper.list(SQL_DIR,
					this.context.getAssets());
			for (String sqlFile : sqlFiles) {
				if (sqlFile.startsWith(UPGRADE_FILE_PREFIX)) {
					final int fileVersion = Integer.parseInt(sqlFile.substring(
							UPGRADE_FILE_PREFIX.length(), sqlFile.length()
									- UPGRADE_FILE_SUFFIX.length()));
					if (fileVersion > oldVersion && fileVersion <= newVersion) {
						execSqlFile(sqlFile, db);
					}
				}
			}
		} catch (IOException exception) {
			throw new RuntimeException("Database upgrade failed", exception);
		}
	}

	/**
	 * This method executes SQL commands found in the given file.
	 * 
	 * @param sqlFile
	 *            Name of the SQL file
	 * @param db
	 *            SQLite database
	 * @throws SQLException
	 *             SQL Exception
	 * @throws IOException
	 *             IO Exception
	 */
	protected void execSqlFile(String sqlFile, SQLiteDatabase db)
			throws SQLException, IOException {
		final List<String> sqlCommands = SqlParser.parseSqlFile(SQL_DIR + "/"
				+ sqlFile, this.context.getAssets());
		try {
			for (String sqlCommand : sqlCommands) {
				db.execSQL(sqlCommand);
			}
		} catch (SQLiteException ex) {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	}
}