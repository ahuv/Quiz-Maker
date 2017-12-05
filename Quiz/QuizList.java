package Quiz;

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
		fr = new FileReader("src/Quiz/AvailableQuiz.txt");
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
	
}
	

