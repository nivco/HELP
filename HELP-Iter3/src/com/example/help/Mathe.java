package com.example.help;


import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
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

public class Mathe extends Activity implements OnClickListener,
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mathe);
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
		question.setText("Question 1");
		answers[0].setText("Answer 1");
		answers[1].setText("Answer 2");
		answers[2].setText("Answer 3");
		answers[3].setText("Answer 4");
		time = (Chronometer) findViewById(R.id.chronometer1);
		time.start();

		String[] arr = { "Answer 1", "Answer 2", "Answer 3",
				"Answer 4" };
		questions = new Question[5];
		questions[0] = new Question("2", "Question 1", arr);
		questions[1] = new Question("2", "Question 2", arr);
		questions[2] = new Question("2", "Question 3", arr);
		questions[3] = new Question("2", "Question 4", arr);
		questions[4] = new Question("2", "Question 5", arr);
		gd = new GestureDetector(new SwipeDetector());
	}

	  public boolean onTouchEvent(MotionEvent event) {
		    if (gd.onTouchEvent(event)) {
		      return true;
		    }
		    return super.onTouchEvent(event);
		  }
	
	public void onClick(View v) {
		paintBlack();
		if (v.getId() == R.id.imageButton1) {
			nextQuestion();
		}
		if (v.getId() == R.id.imageButton2) {
			prevQuestion();
		}

	}
	public void paintBlack()
	{
		selectionList.clearCheck();
		for(int i = 0 ; i < answers.length ; i++)
			answers[i].setTextColor(Color.BLACK);
	}
	
	public void nextQuestion()
	{
		if (quesNum < questions.length - 1)
			quesNum++;
		else 
			return;
		question.setText(questions[quesNum].getQuestion());
		answers[0].setText(questions[quesNum].getAnswers()[0]);
		answers[1].setText(questions[quesNum].getAnswers()[1]);
		answers[2].setText(questions[quesNum].getAnswers()[2]);
		answers[3].setText(questions[quesNum].getAnswers()[3]);
	}
	
	public void prevQuestion()
	{
		if (quesNum > 0)
			quesNum--;
		else 
			return;
		question.setText(questions[quesNum].getQuestion());
		answers[0].setText(questions[quesNum].getAnswers()[0]);
		answers[1].setText(questions[quesNum].getAnswers()[1]);
		answers[2].setText(questions[quesNum].getAnswers()[2]);
		answers[3].setText(questions[quesNum].getAnswers()[3]);
	}
	
	public void paintBlue(int toPaint)
	{
		for(int i = 0; i < answers.length ; i++)
		{
			if(i == toPaint)
				answers[i].setTextColor(Color.BLUE);
			else
				answers[i].setTextColor(Color.BLACK);
		}
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.rAns0:
			paintBlue(0);
			break;
		case R.id.rAns1:
			paintBlue(1);
			break;
		case R.id.rAns2:
			paintBlue(2);
			break;
		case R.id.rAns3:
			paintBlue(3);
			break;

		}

	}

	@Override
	protected void onPause() {

		super.onPause();
		time.stop();
	}

	private void onRightSwipe() {
		paintBlack();
		nextQuestion();
	}

	private void onLeftSwipe() {
		paintBlack();
		prevQuestion();
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
					Mathe.this.onLeftSwipe();

					// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Mathe.this.onRightSwipe();
				}
			} catch (Exception e) {

			}
			return false;
		}
	}
}
