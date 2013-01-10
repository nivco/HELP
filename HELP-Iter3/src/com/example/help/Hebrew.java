package com.example.help;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.database2.JSONParser;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Hebrew extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	GestureDetector gd;
	TextView question;
	ImageButton nextQ, prevQ;
	RadioGroup selectionList;
	RadioButton[] answers;
	String setData;
	Chronometer time;
	Question[] questions;
	int quesNum;

	int[] studentAnswers;
	String[] rightAnswers;
	// //////////////////////////////////////////////////////////////////////////////
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> questionsList;

	// url to get all products list
	private static String url_all_products = "http://itayco.hostei.com/get_all_rows.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_QUESTIONS = "questions";
	private static final String TAG_QUESTION = "question";
	private static final String TAG_ANSWER1 = "answer1";
	private static final String TAG_ANSWER2 = "answer2";
	private static final String TAG_ANSWER3 = "answer3";
	private static final String TAG_ANSWER4 = "answer4";
	private static final String TAG_RIGHT_ANSWER = "rightAnswer";
	private static final String TAG_QUESTION_LEVEL = "questionLevel";

	// products JSONArray
	JSONArray questionsJSON = null;// //////////

	// ///////////////////////////////////////////////////////////////////////////////////////

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hebrew);
		init();

	}

	private void init() {
		question = (TextView) findViewById(R.id.tvQues);
		quesNum = 0;
		nextQ = (ImageButton) findViewById(R.id.imageButton1);
		prevQ = (ImageButton) findViewById(R.id.imageButton2);
		selectionList = (RadioGroup) findViewById(R.id.rgAnswers);
		nextQ.setOnClickListener(this);
		prevQ.setOnClickListener(this);
		selectionList.setOnCheckedChangeListener(this);
		answers = new RadioButton[4];
		answers[0] = (RadioButton) findViewById(R.id.rAns0);
		answers[1] = (RadioButton) findViewById(R.id.rAns1);
		answers[2] = (RadioButton) findViewById(R.id.rAns2);
		answers[3] = (RadioButton) findViewById(R.id.rAns3);
		question.setText("?מהו אלמנך");
		answers[0].setText("לוח שנה");
		answers[1].setText("אדם שאישתו איננה בחיים");
		answers[2].setText("עוזר במעבדה");
		answers[3].setText("אל מן המיתולוגיה המצרית");
		time = (Chronometer) findViewById(R.id.chronometer1);
		time.start();
		// ////////////////////////////////////////////////////////////////////////////////
		// Hashmap for ListView
		questionsList = new ArrayList<HashMap<String, String>>();

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET",
				params);

		// Check your log cat for JSON reponse
		Log.d("All Questions: ", json.toString());

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				questionsJSON = json.getJSONArray(TAG_QUESTIONS);

				questions = new Question[questionsJSON.length()];
				rightAnswers = new String[questionsJSON.length()];

				// looping through All Products
				for (int i = 0; i < questionsJSON.length(); i++) {
					JSONObject c = questionsJSON.getJSONObject(i);

					// Storing each json item in variable
					String question = c.getString(TAG_QUESTION);
					String answer1 = c.getString(TAG_ANSWER1);
					String answer2 = c.getString(TAG_ANSWER2);
					String answer3 = c.getString(TAG_ANSWER3);
					String answer4 = c.getString(TAG_ANSWER4);
					String rightAnswer = c.getString(TAG_RIGHT_ANSWER);
					String questionLevel = c.getString(TAG_QUESTION_LEVEL);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_QUESTION, question);
					map.put(TAG_ANSWER1, answer1);
					map.put(TAG_ANSWER2, answer2);
					map.put(TAG_ANSWER3, answer3);
					map.put(TAG_ANSWER4, answer4);
					map.put(TAG_RIGHT_ANSWER, rightAnswer);
					map.put(TAG_QUESTION_LEVEL, questionLevel);

					// adding HashList to ArrayList
					questionsList.add(map);
					String[] arr = { map.get("answer1"), map.get("answer2"),
							map.get("answer3"), map.get("answer4") };
					questions[i] = new Question(map.get("rightAnswer"),
							map.get("question"), arr);
					rightAnswers[i] = map.get("rightAnswer");
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// /////////////////////////////////////////////////////////////////////////////////
		// String[] arr0 = { "מכשיר למדידת לחץ", "כלי המשמש ללימוד נגינה",
		// "אדם זקן מאוד",
		// "מומחה בתחום החקלאות" };
		// String[] arr1 = { "לוח שנה", "אדם שאישתו איננה בחיים", "עוזר במעבדה",
		// "אל מן המיתולוגיה המצרית" };
		// String[] arr2 = { "חוסר סדר, כאוס", "מרפאה", "משמעות כפולה",
		// "וויסות" };
		// String[] arr3 = { "בית חולים", "מחלה כרונית", "לבריאות",
		// "בית משרדים גדול" };
		// String[] arr4 = { "סוג של קפה", "לשון בין לאומית", "במהירות",
		// "נסיון רב מאוד" };
		// String[] arr5 = { "בזעם רב", "באומץ וגבורה", "בעצב",
		// "בנחת וברוגע" };
		// String[] arr6 = { "הד", "בת שנולדה מחוץ לנישואין", "חיוך",
		// "שותפה לשיחה" };
		// String[] arr7 = { "חותמת", "אישור רשמי", "גושפנקא",
		// "העתק" };
		// String[] arr8 = { "אוגדה גדולה", "קרוב משפחה מרוחק", "דבר מיותר",
		// "כינוי לבגד" };
		// String[] arr9 = { "מלתחה", "תסרוקת", "מקלחת",
		// "מחלת עור" };
		// questions = new Question[10];
		// questions[0] = new Question(4, "?מהו אגרונום", arr0);
		// questions[1] = new Question(1, "?מהו אלמנך", arr1);
		// questions[2] = new Question(2, "?מהי משמעות המילה אמבולטוריה", arr2);
		// questions[3] = new Question(3,
		// "?לאיזו מן המילים משמעות נרדפת למילה אסותא", arr3);
		// questions[4] = new Question(2, "?מהי משמעות המילה אספרנטו", arr4);
		// questions[5] = new Question(4,
		// "?'לאיזו מילה משמעות הפוכה לביטוי: 'בחירוק שיניים", arr5);
		// questions[6] = new Question(3, "?מהי משמעות המילה בת שחוק", arr6);
		// questions[7] = new Question(4, "?אילו מבין הבאים יוצא דופן", arr7);
		// questions[8] = new Question(4, "?'מהי משמעות הביטוי: 'גיס חמישי",
		// arr8);
		// questions[9] = new Question(1, "?מהי משמעות המילה גרדרובה", arr9);

		studentAnswers = new int[questionsJSON.length()];

		gd = new GestureDetector(new SwipeDetector());
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (gd.onTouchEvent(event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	public void onClick(View v) {

		selectionList.clearCheck();
		answers[0].setTextColor(Color.BLACK);
		answers[1].setTextColor(Color.BLACK);
		answers[2].setTextColor(Color.BLACK);
		answers[3].setTextColor(Color.BLACK);
		if (v.getId() == R.id.imageButton1) {
			if (quesNum == questions.length - 1) {
				// /////////////////////////////////////////////////////////
				int[] intRightAnswers = new int[rightAnswers.length];
				for (int i = 0; i < rightAnswers.length; i++) {
					intRightAnswers[i] = Integer.parseInt(rightAnswers[i]);
				}
				int res = 0;

				for (int i = 0; i < rightAnswers.length; i++) {
					if (intRightAnswers[i] == studentAnswers[i])
						res++;
				}
				res = res * 10;
				String str = Integer.toString(res);

				String url = "http://itayco.hostei.com/create_statistics_temp.php";

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);

				try {

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							3);
					nameValuePairs.add(new BasicNameValuePair("english", ""));
					nameValuePairs.add(new BasicNameValuePair("math", ""));
					nameValuePairs.add(new BasicNameValuePair("hebrew", str));

					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(post);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
				// }
				// /////////////////////////////////////////////////////////
				Class s;
				try {
					s = Class.forName("com.example.help.Statistics");
					Intent activity = new Intent(Hebrew.this, s);
					startActivity(activity);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (quesNum < questions.length - 1)
				quesNum++;
			question.setText(questions[quesNum].getQuestion());
			answers[0].setText(questions[quesNum].getAnswers()[0]);
			answers[1].setText(questions[quesNum].getAnswers()[1]);
			answers[2].setText(questions[quesNum].getAnswers()[2]);
			answers[3].setText(questions[quesNum].getAnswers()[3]);
		}
		if (v.getId() == R.id.imageButton2) {
			if (quesNum > 0)
				quesNum--;
			question.setText(questions[quesNum].getQuestion());
			answers[0].setText(questions[quesNum].getAnswers()[0]);
			answers[1].setText(questions[quesNum].getAnswers()[1]);
			answers[2].setText(questions[quesNum].getAnswers()[2]);
			answers[3].setText(questions[quesNum].getAnswers()[3]);
		}

	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.rAns0:
			studentAnswers[quesNum] = 1;

			answers[0].setTextColor(Color.BLUE);
			answers[1].setTextColor(Color.BLACK);
			answers[2].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			break;
		case R.id.rAns1:
			studentAnswers[quesNum] = 2;

			answers[1].setTextColor(Color.BLUE);
			answers[2].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			answers[0].setTextColor(Color.BLACK);
			break;
		case R.id.rAns2:
			studentAnswers[quesNum] = 3;
			answers[2].setTextColor(Color.BLUE);
			answers[0].setTextColor(Color.BLACK);
			answers[1].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			break;
		case R.id.rAns3:
			studentAnswers[quesNum] = 4;
			answers[3].setTextColor(Color.BLUE);
			answers[1].setTextColor(Color.BLACK);
			answers[2].setTextColor(Color.BLACK);
			answers[0].setTextColor(Color.BLACK);
			break;

		}

	}

	// public void stats()
	// {
	// for(int i = 0; i < rightAnswers.length; i++)
	// {
	// rightAnswers[i] = atoi();
	// }
	// }

	// for(int j = 0; j < rightAn)
	// {
	//
	// }

	// public String[] studAns(String[] arr)
	// {
	// for(int i = 0; i < arr.length; i++)
	// arr[i] = studentAnswers[i];
	// return arr;
	// }
	//
	// public String[] righAns(String[] arr)
	// {
	// for(int i = 0; i < arr.length; i++)
	// arr[i] = rightAnswers[i];
	// return arr;
	// }

	@Override
	protected void onPause() {

		super.onPause();
		time.stop();
	}

	private void onRightSwipe() {
		selectionList.clearCheck();
		answers[0].setTextColor(Color.BLACK);
		answers[1].setTextColor(Color.BLACK);
		answers[2].setTextColor(Color.BLACK);
		answers[3].setTextColor(Color.BLACK);
		if (quesNum < questions.length - 1)
			quesNum++;
		question.setText(questions[quesNum].getQuestion());
		answers[0].setText(questions[quesNum].getAnswers()[0]);
		answers[1].setText(questions[quesNum].getAnswers()[1]);
		answers[2].setText(questions[quesNum].getAnswers()[2]);
		answers[3].setText(questions[quesNum].getAnswers()[3]);
	}

	private void onLeftSwipe() {
		selectionList.clearCheck();
		answers[0].setTextColor(Color.BLACK);
		answers[1].setTextColor(Color.BLACK);
		answers[2].setTextColor(Color.BLACK);
		answers[3].setTextColor(Color.BLACK);
		if (quesNum > 0)
			quesNum--;
		question.setText(questions[quesNum].getQuestion());
		answers[0].setText(questions[quesNum].getAnswers()[0]);
		answers[1].setText(questions[quesNum].getAnswers()[1]);
		answers[2].setText(questions[quesNum].getAnswers()[2]);
		answers[3].setText(questions[quesNum].getAnswers()[3]);
	}

	private class SwipeDetector extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 200;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diff = e1.getX() - e2.getX();

				if (diffAbs > SWIPE_MAX_OFF_PATH)
					return false;

				// Left swipe
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Hebrew.this.onLeftSwipe();

					// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Hebrew.this.onRightSwipe();
				}
			} catch (Exception e) {

			}
			return false;
		}
	}
}
