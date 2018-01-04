
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Marks
{
	private ArrayList<String> names;
	private ArrayList<Integer> grades;
	private ArrayList<String> quizzes;
	Scanner scan;

	// instantiates the names and grades ArrayLists and calls readFile()
	public Marks() throws FileNotFoundException
	{
		names = new ArrayList<String>();
		grades = new ArrayList<Integer>();
		quizzes = new ArrayList<String>();

		readFile();
	}

	// calls Marks file and reads the names and grades into ArrayLists
	private void readFile() throws FileNotFoundException
	{
		FileReader marksFile = new FileReader("Marks.txt");
		scan = new Scanner(marksFile);

		while (scan.hasNext())
		{
			names.add(scan.next());
			grades.add(scan.nextInt());
			quizzes.add(scan.next());
		}

	}

	/**
	 * @return the names
	 */
	public ArrayList<String> getNames()
	{
		return names;
	}

	/**
	 * @return the grades
	 */
	public ArrayList<Integer> getGrades()
	{
		return grades;
	}

	/**
	 * @return the quizzes
	 */
	public ArrayList<String> getQuizzes()
	{
		return quizzes;
	}

}
