package com.dong.mp3player;

import android.app.Activity;
import android.os.Bundle;

import com.dong.R;
import com.dong.util.UpdateManager;

public class DownloadActivity extends Activity {

	private UpdateManager updateManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		
		updateManager = new UpdateManager(this);
		updateManager.checkUpdateInfo();
	}
}
