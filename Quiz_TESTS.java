import static org.junit.Assert.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Test;



public class Quiz_TESTS {
	
	
	@Test (expected = java.io.FileNotFoundException.class)
	public void FileNotFoundThrowsException() throws FileNotFoundException, IOException, EmptyLineException, InputMismatchException
	{
		String filename = "Quiz3.txt";
		FileReader fr = new FileReader(filename);
		Scanner scan = new Scanner(fr);
		
		Quiz quiz = new Quiz(filename);
	}
	
	
	@Test (expected = EmptyLineException.class)
	public void BlankInFileThrowsException() throws EmptyLineException, FileNotFoundException, IOException, InputMismatchException 
	{
		String filename = "Quiz2.txt";
		FileReader fr = new FileReader(filename);
		Scanner scan = new Scanner(fr);
		Quiz quiz = new Quiz(filename);
		
	}
	
	
	
	@Test
	public void AssertAllEqualLengthArrayLists() throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException
	{
		String filename = "Quiz1.txt";
		FileReader fr = new FileReader(filename);
		Scanner scan = new Scanner(fr);
		Quiz quiz = new Quiz(filename);
		
		int length = quiz.numQuestions;
		
		assertEquals(quiz.getQuestions().size(), length);
		assertEquals(quiz.getAnswerOptions().size(), length);
		assertEquals(quiz.getAnswers().size(), length);
		
	}
	
	
	
	


}
