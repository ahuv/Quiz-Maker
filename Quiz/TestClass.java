package Quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClass
{

	public static void main(String[] args)
			throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException
	{

		Scanner sc = new Scanner(System.in);
		Users users = new Users();
		boolean found = true;
		String name = "";

		System.out.println("If you are a student, enter 1. If you are at teacher, enter 2.");
		int choice = sc.nextInt();
		sc.nextLine();
		switch (choice)
		{

		case 1:
			do
			{
				// checks student identification
				try
				{
					found = true;
					System.out.println("Please enter your username:");
					name = sc.nextLine();
					System.out.println("Please enter your password:");
					String password = sc.nextLine();
					users.checkStudentPassword(name, password);
					// checkIdentification(users,sc,name);

				}
				catch (IncorrectPasswordException | IncorrectUsernameException e)
				{
					System.out.println(e);
					found = false;
				}
			}
			while (found == false);

			System.out.println("Hello " + name);

			String quiz1 = "src/Quiz1.txt";

			Quiz quiz = new Quiz(quiz1);

			// take quiz
			ArrayList<Character> studentAnswers = takeQuiz(quiz, sc);

			// display results
			QuizMarker marker = new QuizMarker(quiz.getAnswers(), studentAnswers);
			displayResults(marker);

			// writes scores to file
			saveScores(marker, name);

			break;
		case 2:
			do
			{
				// checks teacher identification
				try
				{
					found = true;
					System.out.println("Please enter your username:");
					name = sc.nextLine();
					System.out.println("Please enter your password:");
					String password = sc.nextLine();
					users.checkTeacherPassword(name, password);
					// checkIdentification(users,sc,name);

				}
				catch (IncorrectPasswordException | IncorrectUsernameException e)
				{
					System.out.println(e);
					found = false;
				}
			}
			while (found == false);

			System.out.println("Enter 1 to create a new quiz. Enter 2 to see students results.");
			int choice2 = sc.nextInt();
			sc.nextLine();
			switch (choice2)
			{
			//
			case 1:
				
				
				break;
			
			//displays students results
			case 2:
				Marks marks = new Marks();
				
				for (int i =0; i < marks.getGrades().size(); i++)
				{
				System.out.println(marks.getNames().get(i)+ ": " + marks.getGrades().get(i));
				}
				break;
			default:
				System.out.println("Incorrect Entry");

			}

			break;
		default:
			System.out.println("Incorrect Entry");
		}

	}

	/*private static void checkIdentification(Users users, Scanner sc)
			throws IncorrectPasswordException, IncorrectUsernameException
	{
		System.out.println("Please enter your username:");
		String name = sc.nextLine();
		System.out.println("Please enter your password:");
		String password = sc.nextLine();
		users.checkStudentPassword(name, password);
	}*/

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
		PrintWriter printWriter = new PrintWriter(new FileWriter("src/Quiz/Marks.txt",true));
		printWriter.append(studentName);
		printWriter.append("\n" + marker.getScore() + "\n");
		printWriter.close();
	}

}
