package com.example.help;

//import com.itayc.st.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Statistics extends Activity implements View.OnClickListener {

	TextView display;
	
	
////////////////////////////////////////////////////////////////////////////////
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> statisticsList;

	// url to get all question list
	private static String url_all_products = "http://itayco.hostei.com/get_statistics.php";
	// url to delete stats
//	private static final String url_delete_product = "http://itayco.hostei.com/delete_row_stats.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STATISTICS = "statistics";
	private static final String TAG_ENGLISH = "english";
	private static final String TAG_MATH = "math";
	private static final String TAG_HEBREW = "hebrew";
	
	String grade = "";
//	String hebrew = "";
	// products JSONArray
	JSONArray statisticsJSON = null;////////////
/////////////////////////////////////////////////////////////////////////////////////////
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
		display = (TextView) findViewById(R.id.textView1);
//		Intent inte = getIntent();
//		hebrew = inte.getStringExtra(TAG_HEBREW);
//////////////////////////////////////////////////////////////////////////////////
		// Hashmap for ListView
		statisticsList = new ArrayList<HashMap<String, String>>();



		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

		// Check your log cat for JSON reponse
		Log.d("All Statistics: ", json.toString());

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				statisticsJSON = json.getJSONArray(TAG_STATISTICS);

//				questions = new Question[questionsJSON.length()];
//				rightAnswers = new String[questionsJSON.length()];

				// looping through All Products
				for (int i = 0; i < statisticsJSON.length(); i++) {
					JSONObject c = statisticsJSON.getJSONObject(i);

					// Storing each json item in variable
					String english = c.getString(TAG_ENGLISH);
					String math = c.getString(TAG_MATH);
					String hebrew = c.getString(TAG_HEBREW);
					

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ENGLISH, english);
					map.put(TAG_MATH, math);
					map.put(TAG_HEBREW, hebrew);
					

					// adding HashList to ArrayList
					statisticsList.add(map);
//					String[] arr = {map.get("answer1"), 
//							map.get("answer2"), map.get("answer3"), map.get("answer4")};
//					questions[i] = new Question(map.get("rightAnswer"), 
//							map.get("question"), arr);
//					rightAnswers[i] = map.get("rightAnswer");
					grade = map.get("hebrew");
					display.setText(grade);
					
				}
				
			} 

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
///////////////////////////////////////////////////////////////////////////////////
		//display.setText("Grade: " + grade);
//		// Check for success tag
//					int success;
//					try {
//						// Building Parameters
//						List<NameValuePair> params2 = new ArrayList<NameValuePair>();
//						params.add(new BasicNameValuePair("hebrew", hebrew));
//
//						// getting product details by making HTTP request
//						JSONObject json2 = jParser.makeHttpRequest(
//								url_delete_product, "POST", params2);
//
//						// check your log for json response
//						Log.d("Delete Product", json2.toString());
//						
//						// json success tag
//						success = json2.getInt(TAG_SUCCESS);
//						if (success == 1) {
//							// product successfully deleted
//							// notify previous activity by sending code 100
//							//Intent i = getIntent();
//							// send result code 100 to notify about product deletion
//							///setResult(100, i);
//							//finish();
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
		
//		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}






	

}	