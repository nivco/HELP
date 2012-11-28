package com.example.help;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String classes[] = { "אנגלית", "חשיבה כמותית", "עברית" };
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setListAdapter(new ArrayAdapter<String>(Menu.this,
				android.R.layout.simple_expandable_list_item_1, classes));

	}
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		Class ourClass;
		try {
			
			if (position == 0)
				ourClass = Class.forName("com.example.help.English");
			else if (position == 1)
				ourClass = Class.forName("com.example.help.ChronometerDemo");
			else
				ourClass = Class.forName("com.example.help.Hebrew");
			
			
			
			Intent activ = new Intent(Menu.this, ourClass);
			startActivity(activ);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

}
