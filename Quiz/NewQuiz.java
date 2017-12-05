package Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NewQuiz {
	
	private ArrayList<String> questions;
	private ArrayList<ArrayList<String>> answerOptions;
	private ArrayList<Character> answers;
	
	private String name;
	private String fullName;
	private int numOptions;
	private int numQuestions;
	
	PrintWriter writer;
	
	
	public NewQuiz(String filename, int numQs, int numOps) throws IOException, DuplicateQuizNameException
	{
		this.name = filename;
		this.fullName = "src/Quiz/" + name + ".txt";
		this.numQuestions = numQs;
		this.numOptions = numOps;
		
		this.questions = new ArrayList<String>(numQuestions);
		this.answerOptions = new ArrayList<ArrayList<String>>(numOptions);
		this.answers = new ArrayList<Character>();
		
		addToQuizList();
	}
	
	
	public void addQuestion(String question) throws EmptyLineException
	{
		if(question.equals(""))
		{
			throw new EmptyLineException("");
		}
		questions.add(question);
	}
	
	public void addOptions(ArrayList<String> options) throws EmptyLineException
	{
		if(options.get(0).equals("") || options.get(1).equals("")
				|| options.get(2).equals("") || options.get(3).equals(""))
		{
			throw new EmptyLineException("");
		}
		answerOptions.add(options);
	}
	
	public void addAnswer(char answer)
	{
		answers.add(answer);
	}
	
	public void viewQuiz()
	{
		System.out.println("Quizname: " + name);
		for(int i= 0; i < questions.size(); i++)
		{
			System.out.println((i+1) + ". " + questions.get(i));
			System.out.println(answerOptions.get(i));
			System.out.println(answers.get(i));
		}
	}
	
	public int getNumQs()
	{
		return numQuestions;
	}
	
	public int getNumOptions()
	{
		return numOptions;
	}
	
	private ArrayList<String> getQuestions()
	{
		return questions;
	}
	
	private ArrayList<ArrayList<String>> getOptions()
	{
		return answerOptions;
	}
	
	private ArrayList<Character> getAnswers()
	{
		return answers;
	}
	
	
	public void makeNewQuiz() throws IOException
	{
		createFile();
		readToFile();
	}
	
	public void createFile()
	{
		try{
			File myFile = new File(fullName);
			
			if(myFile.createNewFile())
			{
				System.out.println("File created.");
			}
			else
			{
				System.out.println("File already exists");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void readToFile() throws IOException
	{
		writer = new PrintWriter(new FileWriter(fullName,true));
		
		for(int i = 0; i < numQuestions; i++)
		{
			writer.append(questions.get(i) + "\n");
			
			for(int n = 0; n < numOptions; n++)
			{
				writer.append(answerOptions.get(i).get(n).toString() + "\n");
			}
			
			writer.append(answers.get(i) + "\n");
		}
		
		writer.close();
	}
	
	public void addToQuizList() throws DuplicateQuizNameException, IOException
	{
		QuizList quizzes = new QuizList();
		
		ArrayList<String> list = new ArrayList<String>();
		list = quizzes.getList();
		
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(fullName))
			{
				throw new DuplicateQuizNameException("");
			}
		}
		
		writer = new PrintWriter(new FileWriter("src/Quiz/AvailableQuiz.txt", true));
		writer.append("\n" + fullName);
		writer.close();
	}
	
	

}
