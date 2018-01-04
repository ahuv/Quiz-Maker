
import static org.junit.Assert.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

public class Quiz_TESTS
{

	String filename;
	Quiz quiz;
	Scanner scan;
	FileReader fr;
	PrintWriter writer;

	//deletes Test file after each method
	@After
	public void closeUp() throws IncorrectQuizNameException, IOException
	{
		QuizList list = new QuizList();
		list.deleteQuiz("Test.txt");
	}

	@Test(expected = java.io.FileNotFoundException.class)
	public void FileNotFoundThrowsException()
			throws FileNotFoundException, IOException, EmptyLineException, InputMismatchException
	{
		filename = "Test.txt";
		fr = new FileReader(filename);
		scan = new Scanner(fr);

		quiz = new Quiz(filename);
	}

	@Test(expected = EmptyLineException.class)
	public void BlankInFileThrowsException()
			throws EmptyLineException, FileNotFoundException, IOException, InputMismatchException
	{
		// creates test file
		filename = "Test.txt";
		writer = new PrintWriter(new FileWriter(filename, true));
		writer.append("What color is the sky?\n");
		writer.append("blue\n");
		writer.append("green\n");
		writer.append("\n");
		writer.append("red\n");
		writer.append("yellow\n");
		writer.append("a");
		writer.close();

		// reads from file
		fr = new FileReader(filename);
		scan = new Scanner(fr);
		quiz = new Quiz(filename);

	}

	@Test
	public void AssertAllEqualLengthArrayLists()
			throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException
	{

		// creates test file
		filename = "Test.txt";
		writer = new PrintWriter(new FileWriter(filename, true));

		writer.append("What color is the sky?\n");
		writer.append("blue\n");
		writer.append("green\n");
		writer.append("red\n");
		writer.append("yellow\n");
		writer.append("a\n");
		writer.append("What color is a leaf?\n");
		writer.append("blue\n");
		writer.append("green\n");
		writer.append("red\n");
		writer.append("yellow\n");
		writer.append("b\n");
		writer.append("What color is the sun?\n");
		writer.append("blue\n");
		writer.append("green\n");
		writer.append("red\n");
		writer.append("yellow\n");
		writer.append("d");
		writer.close();

		// reads from file
		fr = new FileReader(filename);
		scan = new Scanner(fr);
		quiz = new Quiz(filename);

		int length = quiz.numQuestions;

		assertEquals(quiz.getQuestions().size(), length);
		assertEquals(quiz.getAnswerOptions().size(), length);
		assertEquals(quiz.getAnswers().size(), length);

	}

}
