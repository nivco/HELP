package com.example.help;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Bundle;

public class StartScreen extends Activity {

	MediaPlayer song;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_screen);
		song = MediaPlayer.create(this, R.raw.punch);
		song.start();
		
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1500);
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					
					Intent openMain = new Intent(
							"com.example.help.Menu");
					
					
					startActivity(openMain);

				}

			} 
		};

		timer.start();
	}

	protected void onPause() {

		super.onPause();
		song.release();
		finish();
	}

}
