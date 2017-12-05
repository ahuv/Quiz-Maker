package Quiz;

import java.util.ArrayList;

public class QuizMarker
{

	private ArrayList<Character> answers;
	private ArrayList<Character> studentAns;
	private int score;

	public QuizMarker(ArrayList<Character> answers, ArrayList<Character> studentAns)
	{
		this.answers = answers;
		this.studentAns = studentAns;
		score = 0;
	}

	public int correctAnswers()
	{
		int correct = 0;

		for (int i = 0; i < answers.size(); i++)
		{
			Character answer = Character.toLowerCase(answers.get(i));
			Character studentAnswer = Character.toLowerCase(studentAns.get(i));
			if (answer.compareTo(studentAnswer) == 0)
			{
				correct++;
			}
		}
		return correct;
	}

	public int incorrectAnswers()
	{
		int incorrect = 0;

		for (int i = 0; i < answers.size(); i++)
		{
			Character answer = Character.toLowerCase(answers.get(i));
			Character studentAnswer = Character.toLowerCase(studentAns.get(i));
			if (!(answer.compareTo(studentAnswer) == 0))
			{
				incorrect++;
			}
		}
		return incorrect;
	}

	public ArrayList<Integer> questionsMissed()
	{
		ArrayList<Integer> missed = new ArrayList<Integer>();
		for (int i = 0; i < answers.size(); i++)
		{
			Character answer = Character.toLowerCase(answers.get(i));
			Character studentAnswer = Character.toLowerCase(studentAns.get(i));
			if (!(answer.compareTo(studentAnswer) == 0))
			{
				int wrong = i + 1;
				missed.add(wrong);
			}
		}

		return missed;
	}

	public char grade()
	{
		score = (int) (100 * ((double) correctAnswers() / answers.size()));

		char grade;
		if (score >= 90)
			grade = 'A';
		else if (score >= 80)
			grade = 'B';
		else if (score >= 70)
			grade = 'C';
		else if (score >= 60)
			grade = 'D';
		else
			grade = 'F';

		return grade;
	}

	public int getScore()
	{
		score = (int) (100 * ((double) correctAnswers() / answers.size()));
		return score;
	}

}
