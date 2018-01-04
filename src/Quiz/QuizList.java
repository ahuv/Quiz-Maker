package Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		File file = new File("CopyFile.txt");
		PrintWriter copyFile = new PrintWriter(new FileWriter("CopyFile.txt", true));

		File oldFile = new File("AvailableQuiz.txt");
		scan = new Scanner(oldFile);
		boolean found = false;

		while (scan.hasNext())
		{
			String line = scan.next();

			if (line.equalsIgnoreCase(QuizName))
			{
				found = true;
			}
			else
			{
				copyFile.append(line + "\n");
			}
		}
		copyFile.close();
		if (!found)
		{
			throw new IncorrectQuizNameException();
		}

		File quizFile = new File(QuizName);
		file.renameTo(oldFile);
		if (quizFile.delete())
		{
			return true;
		}
		return false;

	}

}
