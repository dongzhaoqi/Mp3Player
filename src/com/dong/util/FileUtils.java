package com.dong.util;

import java.io.File;

import android.os.Environment;

public class FileUtils {

	String sdCardRoot;

	public FileUtils() {
		sdCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
	}

	public boolean isFileExists(String fileName, String path) {
		File file = new File(sdCardRoot + path + File.separator + fileName);
		return file.exists();
	}

}
