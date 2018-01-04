
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewQuiz_TESTS
{

	private ArrayList<String> questions;
	private ArrayList<ArrayList<String>> answerOptions;
	private ArrayList<Character> answers;

	private String name;
	private String fullName;
	private int numOptions;
	private int numQuestions;

	PrintWriter writer;

	private NewQuiz quiz;

	@Before
	public void setUp()
	{
		questions = new ArrayList<String>();
		answerOptions = new ArrayList<ArrayList<String>>();
		answers = new ArrayList<Character>();
		name = "test";

	}

	@After
	public void closeUp() throws IncorrectQuizNameException, IOException
	{
		QuizList list = new QuizList();
		list.deleteQuiz("test.txt");
	}

	@Test(expected = DuplicateQuizNameException.class)
	public void TestExceptionThrownForDuplicateQuizName() throws IOException, DuplicateQuizNameException
	{

		int numQs = 10;
		quiz = new NewQuiz("test", numQs, 4);
		quiz = new NewQuiz("test", numQs, 4);

	}

	@Test
	public void TestArrayListLengthsMatchUserInput() throws IOException, DuplicateQuizNameException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		assertEquals(quiz.getNumQs(), numQuestions);
		assertEquals(quiz.getNumOptions(), 4);

	}

	@Test
	public void TestAddQuestionAddsAStringToQuestionArrayList()
			throws DuplicateQuizNameException, IOException, EmptyLineException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		String question = "What is your name?";

		quiz.addQuestion(question);
	}

	@Test(expected = EmptyLineException.class)
	public void TestAddQuestionThrowsEmptyLineException()
			throws DuplicateQuizNameException, IOException, EmptyLineException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		String question = "";

		quiz.addQuestion(question);
	}

	@Test
	public void TestAddAnswerOptionsAddsOptionsArrayListtoAnswerOptionsArrayList()
			throws DuplicateQuizNameException, IOException, EmptyLineException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		String optionA = "A";
		String optionB = "B";
		String optionC = "C";
		String optionD = "D";

		ArrayList<String> options = new ArrayList<String>(4);
		options.add(optionA);
		options.add(optionB);
		options.add(optionC);
		options.add(optionD);

		quiz.addOptions(options);
	}

	@Test(expected = EmptyLineException.class)
	public void TestAddAnswerOptionsThrowsEmptyLineException()
			throws DuplicateQuizNameException, IOException, EmptyLineException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		ArrayList<String> options = new ArrayList<String>(4);
		options.add("");
		options.add("");
		options.add("");
		options.add("");

		quiz.addOptions(options);
	}

	@Test
	public void TestAddAnswerAddsAnswerstoAnswersArrayList() throws DuplicateQuizNameException, IOException
	{

		numQuestions = 10;

		quiz = new NewQuiz("test", numQuestions, 4);

		char answer = 'A';

		quiz.addAnswer(answer);
	}

	@Test
	public void TestsViewQuizDisplaysQuizCorrectly() throws IOException, DuplicateQuizNameException, EmptyLineException
	{
		quiz = new NewQuiz("test", 1, 4);

		quiz.addQuestion("question");

		ArrayList<String> options = new ArrayList<String>(4);
		options.add("A");
		options.add("B");
		options.add("C");
		options.add("D");

		quiz.addOptions(options);

		quiz.addAnswer('A');

		quiz.viewQuiz();
	}

	@Test
	public void TestCreateNewFileForNewQuiz() throws IOException, DuplicateQuizNameException
	{
		quiz = new NewQuiz("test", 1, 4);

		quiz.createFile();
	}

	@Test
	public void TestWriteToFileForNewQuizReadsArrayListsIntoNewFile()
			throws IOException, DuplicateQuizNameException, EmptyLineException
	{
		quiz = new NewQuiz("test", 1, 4);

		quiz.addQuestion("question");

		ArrayList<String> options = new ArrayList<String>(4);
		options.add("A");
		options.add("B");
		options.add("C");
		options.add("D");

		quiz.addOptions(options);

		quiz.addAnswer('A');

		quiz.makeNewQuiz();
	}

	@Test(expected = DuplicateQuizNameException.class)
	public void TestAddToQuizListAddsQuizThrowsDuplicateException() throws IOException, DuplicateQuizNameException
	{
		quiz = new NewQuiz("test", 1, 4);

		quiz.addToQuizList();
	}

}
