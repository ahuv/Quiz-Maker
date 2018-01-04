

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class QuizMarker_TESTS
{
	private ArrayList<Character> answers;
	private ArrayList<Character> studentAns;
	private QuizMarker marker;

	@Before
	public void setUp() 
	{   
		answers = new ArrayList<Character>();
		studentAns = new ArrayList<Character>();
	}

	@Test
	public void TestsCorrectAnswerReturnsCorrectNumberIfAllAnswersAreCorrect()
	{

		answers.add('A');
		answers.add('B');
		answers.add('C');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');

		marker = new QuizMarker(answers, studentAns);

		assertEquals(marker.correctAnswers(), 3);
	}

	@Test
	public void TestsCorrectAnswerReturnsCorrectNumberIfDifferentCases()
	{

		answers.add('A');
		answers.add('b');
		answers.add('c');
		studentAns.add('a');
		studentAns.add('B');
		studentAns.add('C');

		marker = new QuizMarker(answers, studentAns);
		assertEquals(marker.correctAnswers(), 3);
	}

	@Test
	public void TestsCorrectAnswerReturnsCorrectNumberIfAllAnswersAreIncorrect()
	{

		answers.add('A');
		answers.add('B');
		answers.add('C');
		studentAns.add('B');
		studentAns.add('A');
		studentAns.add('B');

		marker = new QuizMarker(answers, studentAns);

		assertEquals(marker.correctAnswers(), 0);
	}
	
	@Test
	public void TestIncorrectAnswerReturnsCorrectNumberIfNoIncorrectAnswers()
	{
		answers.add('a');
		answers.add('b');
		answers.add('c');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');

		marker = new QuizMarker(answers, studentAns);
		assertEquals(marker.incorrectAnswers(), 0);
	}
	
	@Test
	public void TestsQuestionsMissedReturnsCorrectArray()
	{
		answers.add('a');
		answers.add('c');
		answers.add('c');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');
		
		marker = new QuizMarker(answers, studentAns);
		
		ArrayList<Integer> questionsMissed = new ArrayList<Integer>();
		questionsMissed.add(2);
		
		assertEquals(marker.questionsMissed(),questionsMissed);
	}
	
	@Test
	public void TestsQuestionsMissedReturnsCorrectArrayIfNoMissedQuestions()
	{
		answers.add('a');
		answers.add('b');
		answers.add('c');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');
		
		marker = new QuizMarker(answers, studentAns);
		
		ArrayList<Integer> questionsMissed = new ArrayList<Integer>();
		
		assertEquals(marker.questionsMissed(),questionsMissed);
	}
	
	@Test
	public void TestsGradeReturnsCorrectGrade()
	{
		answers.add('a');
		answers.add('b');
		answers.add('c');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');
		
		marker = new QuizMarker(answers, studentAns);
		
		assertEquals(marker.grade(),'A');
	}
	
	@Test
	public void TestsGradeReturnsCorrectGradeIfNoCorrectQuestions()
	{
		answers.add('c');
		answers.add('a');
		answers.add('b');
		studentAns.add('A');
		studentAns.add('B');
		studentAns.add('C');
		
		marker = new QuizMarker(answers, studentAns);
		
		assertEquals(marker.grade(),'F');
	}
}
