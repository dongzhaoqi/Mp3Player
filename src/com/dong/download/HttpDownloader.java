package com.dong.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {

	public static void main(String[] args) {
		String result = download("http://www.baidu.com");
		System.out.println("result:" + result);
	}

	public static String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public int downloadFile(String urlStr, String path, String fileName) {
		return 0;

	}
}
