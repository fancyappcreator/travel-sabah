package com.fancyapp.github.travel.sabah.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * This class handles package's version information.
 * http://www.michenux.net/android-database-sqlite-creation-upgrade-245.html
 */
public final class VersionUtil {
	private VersionUtil() {
	}

	/**
	 * This method retrieves version code of the package.
	 * 
	 * @param context
	 *            Application context
	 * @return int
	 * @throws NameNotFoundException
	 *             Name not found exception
	 */
	public static int getVersionCode(Context context)
			throws NameNotFoundException {
		final PackageInfo manager = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0);

		return manager.versionCode;
	}

	/**
	 * This method retrieves version name of the package.
	 * 
	 * @param context
	 *            Application context
	 * @return String
	 * @throws NameNotFoundException
	 *             Name not found exception
	 */
	public static String getVersionName(Context context)
			throws NameNotFoundException {
		final PackageInfo manager = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0);

		return manager.versionName;
	}
}