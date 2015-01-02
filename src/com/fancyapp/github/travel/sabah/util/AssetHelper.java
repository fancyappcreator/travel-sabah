package com.fancyapp.github.travel.sabah.util;

import java.io.IOException;
import java.util.Arrays;

import android.content.res.AssetManager;

/**
 * This class handles assets folder's content related information.
 */
public class AssetHelper {
	
	private AssetHelper() {
	}

	/**
	 * This method checks whether a file with the given name and path exists
	 * inside assets folder.
	 * 
	 * @param fileName
	 *            File name
	 * @param path
	 *            File path
	 * @param assetManager
	 *            Asset manager object
	 * @return boolean
	 * @throws IOException
	 *             IO Exception
	 */
	public static boolean exists(String fileName, String path,
			AssetManager assetManager) throws IOException {
		for (String currentFileName : assetManager.list(path)) {
			if (currentFileName.equals(fileName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This method lists out all the files in the given path inside assets
	 * 
	 * @param path
	 *            Folder path
	 * @param assetManager
	 *            Asset manager object
	 * @return String[]
	 * @throws IOException
	 *             IO Exception
	 */
	public static String[] list(String path, AssetManager assetManager)
			throws IOException {
		final String[] files = assetManager.list(path);
		Arrays.sort(files);

		return files;
	}
}
