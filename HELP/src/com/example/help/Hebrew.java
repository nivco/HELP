package com.example.help;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Hebrew extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	TextView question;
	ImageButton nextQ, prevQ;
	RadioGroup selectionList;
	RadioButton[] answers;
	String setData;
	Chronometer time;
	Question[] questions;
	int quesNum;

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
		question.setText("שאלה 1");
		answers[0].setText("תשובה 1");
		answers[1].setText("תשובה 2");
		answers[2].setText("תשובה 3");
		answers[3].setText("תשובה 4");
		time = (Chronometer) findViewById(R.id.chronometer1);
		time.start();
		String[] arr = { "     תשובה 1", "     תשובה 2", "     תשובה 3", "     תשובה 4" };
		questions = new Question[5];
		questions[0] = new Question(2, "שאלה 1", arr);
		questions[1] = new Question(2, "שאלה 2", arr);
		questions[2] = new Question(2, "שאלה 3", arr);
		questions[3] = new Question(2, "שאלה 4", arr);
		questions[4] = new Question(2, "שאלה 5", arr);

	}


	public void onClick(View v) {
		selectionList.clearCheck();
		answers[0].setTextColor(Color.BLACK);
		answers[1].setTextColor(Color.BLACK);
		answers[2].setTextColor(Color.BLACK);
		answers[3].setTextColor(Color.BLACK);
		if (v.getId() == R.id.imageButton1) {
			if(quesNum < questions.length -1)
				quesNum++;
			question.setText(questions[quesNum].getQuestion());
			answers[0].setText(questions[quesNum].getAnswers()[0]);
			answers[1].setText(questions[quesNum].getAnswers()[1]);
			answers[2].setText(questions[quesNum].getAnswers()[2]);
			answers[3].setText(questions[quesNum].getAnswers()[3]);
		}
		if (v.getId() == R.id.imageButton2) {
			if(quesNum > 0)
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
			answers[0].setTextColor(Color.BLUE);
			answers[1].setTextColor(Color.BLACK);
			answers[2].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			break;
		case R.id.rAns1:
			answers[1].setTextColor(Color.BLUE);
			answers[2].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			answers[0].setTextColor(Color.BLACK);
			break;
		case R.id.rAns2:

			answers[2].setTextColor(Color.BLUE);
			answers[0].setTextColor(Color.BLACK);
			answers[1].setTextColor(Color.BLACK);
			answers[3].setTextColor(Color.BLACK);
			break;
		case R.id.rAns3:
			answers[3].setTextColor(Color.BLUE);
			answers[1].setTextColor(Color.BLACK);
			answers[2].setTextColor(Color.BLACK);
			answers[0].setTextColor(Color.BLACK);
			break;

		}

	}

	@Override
	protected void onPause() {

		super.onPause();
		time.stop();
	}

}
