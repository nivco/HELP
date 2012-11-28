package com.example.help;

public class Question {
	
	private int rightAnswer;
	private String question;
	private String[] answers;
	
	public Question(int rightAnswer, String question, String[] answers)
	{
		this.answers = answers;
		this.rightAnswer = rightAnswer;
		this.question = question;
	}

	public int getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	
	
}
