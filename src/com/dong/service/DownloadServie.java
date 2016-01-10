package com.dong.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dong.download.HttpDownloader;
import com.dong.model.Mp3Info;

public class DownloadServie extends Service {

	public static final String baseUrl = "http://192.168.1.127:8080/mp3/";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	class DownloadThread implements Runnable {

		private Mp3Info mp3Info;

		public DownloadThread(Mp3Info mp3Info) {
			this.mp3Info = mp3Info;
		}

		@Override
		public void run() {

			String mp3Url = baseUrl + mp3Info.getMp3Name();
			HttpDownloader httpDownloader = new HttpDownloader();
		}
	}

}
