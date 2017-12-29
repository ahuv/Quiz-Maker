

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizList {
	
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
		while(scan.hasNext())
		{
			String line = scan.nextLine();
			
			list.add(line);
		}
	}
	
	public ArrayList<String> getList()
	{
		return list;
	}
	
	public void deleteQuiz(String FileName) throws IncorrectQuizNameException
	{
		for (String s : list)
		{
			if (s.equalsIgnoreCase(FileName))
			{
				File file = new File(FileName);
				file.delete();
				return;
			}
		}
		
		throw new IncorrectQuizNameException();
	}
	
}
	

