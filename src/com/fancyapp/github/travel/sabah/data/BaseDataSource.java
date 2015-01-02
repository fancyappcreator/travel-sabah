package com.fancyapp.github.travel.sabah.data;

import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fancyapp.github.travel.sabah.util.SqliteHelper;
import com.fancyapp.github.travel.sabah.util.VersionUtil;

public class BaseDataSource {
	/**
	 * Constant for the name of the database.
	 */
	public static final String DATABASE_NAME = "TravelSabah.sqlite";
	private final String TAG = this.getClass().getSimpleName();
	private SQLiteDatabase database;
	private SqliteHelper dbHelper;
	private Context context;

	/**
	 * Overloaded constructor for BaseDataSource class
	 * 
	 * @param context
	 *            Application context
	 */
	public BaseDataSource(Context context) {
		try {
			this.context = context;
			this.dbHelper = new SqliteHelper(context, DATABASE_NAME, null,
					VersionUtil.getVersionCode(context));
		} catch (NameNotFoundException ex) {
			Log.e(TAG, "Couldn't find package information in PackageManager",
					ex);
		} catch (IOException ex) {
			Log.e(TAG, "IOException has encountered", ex);
		}
	}

	/**
	 * Open data source.
	 * 
	 * @throws SQLException
	 *             SQL Exception
	 */
	public void open() throws SQLException {
		setDatabase(dbHelper.openDatabase());
	}

	/**
	 * Close data source.
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * @param database
	 *            The database to set.
	 */
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * @return Returns the database.
	 */
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void refreshDB() throws SQLException {
		delete();
		createDB();
	}

	private void delete() throws SQLException {
		dbHelper.deleteDatabase();
	}

	private void createDB() throws SQLException {
		new BaseDataSource(context);
	}
}
