package Quiz;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz
{

	protected ArrayList<String> questions;
	protected ArrayList<ArrayList<String>> answerOptions;
	protected ArrayList<Character> answers;
	
	final protected int NUM_OPTIONS = 4;
	protected int numQuestions= 0;

	private Scanner scan;

	public Quiz(String filename) throws FileNotFoundException, IOException, EmptyLineException
	{
		questions = new ArrayList<String>();
		answerOptions = new ArrayList<ArrayList<String>>();
		answers = new ArrayList<Character>();

		FileReader fr = new FileReader(filename);
		scan = new Scanner(fr);

		readFile();
	}

	public void readFile() throws EmptyLineException
	{
		while (scan.hasNext())
		{
			addQuestion();
			addAnswerOption();
			addAnswer();
			numQuestions++;
		}
	}

	public void addQuestion() throws EmptyLineException
	{
		String line = scan.nextLine();
		
		if (line.equals(""))
		{
			throw new EmptyLineException("Empty line.");
		}
		else
		{
			questions.add(line);
		}
	}

	public void addAnswerOption() throws EmptyLineException
	{

		ArrayList<String> options = new ArrayList<>(NUM_OPTIONS);

		for (int i = 0; i < NUM_OPTIONS; i++)
		{
			String line = scan.nextLine();
			
			if (line.equals(""))
			{
				throw new EmptyLineException("Empty line.");
			}

			options.add(line);
		}

		answerOptions.add(options);

	}

	public void addAnswer() throws EmptyLineException
	{

		Character line = scan.nextLine().charAt(0);
		
		if (line.equals(""))
		{
			throw new EmptyLineException("Empty line.");
		}

		answers.add(line);

	}

	public ArrayList<String> getQuestions()
	{
		ArrayList<String> questions2 = new ArrayList<>();

		for (int i = 0; i < questions.size(); i++)
		{
			questions2.add(questions.get(i));
		}
		return questions2;

	}

	public ArrayList<ArrayList<String>> getAnswerOptions()
	{
		ArrayList<ArrayList<String>> answerOptions2 = new ArrayList<>();

		for (int i = 0; i < answerOptions.size(); i++)
		{

			ArrayList<String> options2 = new ArrayList<>();

			for (int j = 0; j < answerOptions.get(i).size(); j++)
			{
				options2.add(answerOptions.get(i).get(j));

			}

			answerOptions2.add(options2);
		}

		return answerOptions2;
	}

	public ArrayList<Character> getAnswers()
	{
		ArrayList<Character> answers2 = new ArrayList<>();

		for (int i = 0; i < answers.size(); i++)
		{
			answers2.add(answers.get(i));
		}
		return answers2;
	}


}