package Quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClass
{

	public static void main(String[] args)
			throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException
	{

		String quiz1 = "src/Quiz1.txt";

		Quiz quiz = new Quiz(quiz1);

		Scanner sc = new Scanner(System.in);

		System.out.println("Please enter your name:");
		String studentName = sc.nextLine();

		// take quiz
		ArrayList<Character> studentAnswers = takeQuiz(quiz, sc);

		// display results
		QuizMarker marker = new QuizMarker(quiz.getAnswers(), studentAnswers);
		displayResults(marker);

		// writes scores to file
		saveScores(marker, studentName);
	}

	private static ArrayList<Character> takeQuiz(Quiz quiz, Scanner sc)
	{
		int quizLength = quiz.getQuestions().size();

		System.out.println("There are " + quizLength + " questions on this quiz.");

		ArrayList<Character> studentAnswers = new ArrayList<Character>();

		for (int i = 0; i < quizLength; i++)
		{
			System.out.println(quiz.getQuestions().get(i));

			for (int j = 0; j < 4; j++)
			{
				System.out.println(quiz.getAnswerOptions().get(i).get(j));
			}

			// allow student to answer answer
			studentAnswers.add(enterAnswer(sc));

			System.out.println();
		}

		return studentAnswers;

	}

	private static Character enterAnswer(Scanner sc)
	{
		String input = sc.nextLine();
		char answer = input.charAt(0);
		validateAnswer(answer);
		return answer;
	}

	private static void validateAnswer(char answer)
	{
		Scanner sc = new Scanner(System.in);
		char val1 = 'a';
		char val2 = 'b';
		char val3 = 'c';
		char val4 = 'd';

		while (answer != val1 && answer != val2 && answer != val3 && answer != val4)
		{
			System.out.print("That is not a valid answer.");
			System.out.println(" Please re-enter your answer: ");
			String input = sc.nextLine();
			answer = input.charAt(0);
		}

	}

	private static void displayResults(QuizMarker marker)
	{

		System.out.println("Score: " + marker.grade());
		System.out.println("correct: " + marker.correctAnswers());
		System.out.println("incorrect: " + marker.incorrectAnswers());
		System.out.println("incorrect questions: " + marker.questionsMissed());
	}

	private static void saveScores(QuizMarker marker, String studentName) throws IOException
	{
		PrintWriter printWriter = new PrintWriter(new FileWriter("src/Results.txt"));
		printWriter.print(studentName);
		printWriter.print("\n" + marker.getScore());
		printWriter.close();
	}

}
