import java.util.ArrayList;

public class QuizMarker {
	
	ArrayList<Character> answers = new ArrayList<Character>();
	ArrayList<Character> givenAns = new ArrayList<Character>();
	
	public QuizMarker(ArrayList<Character> correctAns, ArrayList<Character> studentAns)
	{
		answers = correctAns;
		givenAns = studentAns;
	}
	
	public int correctAnswers()
	{
		int correct = 0;
		
		for ( int i = 0; i < answers.size(); i++)
		{
			if (answers.get(i).compareTo(givenAns.get(i)) ==0)
			{
				correct++;
			}
		}
		return correct;
	}
	
	public int incorrectAnswers()
	{
		int incorrect = 0;
		for ( int i = 0; i < answers.size(); i++)
		{
			if (!(answers.get(i).compareTo(givenAns.get(i)) ==0))
			{
				incorrect++;
			}
		}
		return incorrect;
	}
	
	
	public ArrayList<Integer> questionsMissed()
	{
		ArrayList<Integer> missed = new ArrayList<Integer>();
		for ( int i = 0; i < answers.size(); i++)
		{
			if (!(answers.get(i).compareTo(givenAns.get(i)) ==0))
			{
				int wrong = i+1;
				missed.add(wrong);
			}
		}
		
		return missed;
	}

}
