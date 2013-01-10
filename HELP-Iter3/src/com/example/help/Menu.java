package com.example.help;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;


public class Menu extends Activity implements OnClickListener {
	ImageButton heb, eng, math;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		init();

	}

	private void init() {
		heb = (ImageButton) findViewById(R.id.heb);
		eng = (ImageButton) findViewById(R.id.eng);
		math = (ImageButton) findViewById(R.id.math);
		heb.setOnClickListener(this);
		eng.setOnClickListener(this);
		math.setOnClickListener(this);
	}

	public void onClick(View v) {
		Class toPlay;
		
		try {
			if (v.getId() == R.id.heb)
				toPlay = Class.forName("com.example.help.Hebrew");
			else if (v.getId() == R.id.eng)
				toPlay = Class.forName("com.example.help.English");
			else 
				toPlay = Class.forName("com.example.help.Mathe");
			

			Intent activity = new Intent(Menu.this, toPlay);
			startActivity(activity);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
