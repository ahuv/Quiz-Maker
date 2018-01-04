
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizList
{

	protected ArrayList<String> list = new ArrayList<String>();

	FileReader fr;
	private Scanner scan;

	public QuizList() throws FileNotFoundException
	{
		fr = new FileReader("AvailableQuiz.txt");
		scan = new Scanner(fr);

		readFile();
	}

	public void readFile()
	{
		while (scan.hasNext())
		{
			String line = scan.nextLine();

			list.add(line);
		}
	}

	public ArrayList<String> getList()
	{
		return list;
	}

	public boolean deleteQuiz(String QuizName) throws IncorrectQuizNameException, IOException
	{
		//delete quiz name from list
		Writer writer = new FileWriter("AvailableQuiz.txt", false);
		
		boolean found;
		
		for(int i = 0; i < list.size(); i++)
		{
			String name = list.get(i);
			if (name.equalsIgnoreCase(QuizName))
 			{
 				found = true;
 			}
 			else
 			{
 				writer.append(name + "\n");
 			}
		}
		writer.close();
		
		//delete quiz file
		File quizFile = new File(QuizName);
		
		if (quizFile.delete())
 		{
 			return true;
 		}
		else
		{
			return false;
		}
 		
	 }
}
