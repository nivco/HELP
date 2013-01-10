package com.example.help;

public class Question {
	
	private String rightAnswer;
	private String question;
	private String[] answers;
	
	public Question(String rightAnswer, String question, String[] answers)
	{
		this.answers = answers;
		this.rightAnswer = rightAnswer;
		this.question = question;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
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
