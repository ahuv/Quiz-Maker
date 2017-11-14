import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClass {

	public static void main(String[] args) throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException {
		
		String quiz1 = "Quiz1.txt";
		
		Quiz quiz = new Quiz(quiz1);
		
		int quizLength = quiz.getQuestions().size();
		
		System.out.println("There are " + quizLength + " questions on this quiz.");
		
		Scanner sc = new Scanner(System.in);
		ArrayList<Character> studentAnswers = new ArrayList<Character>();
		
		for (int i = 0; i < quizLength; i++)
		{
			System.out.println(quiz.getQuestions().get(i));
			
			for (int j = 0; j < 4; j++)
			{
			System.out.println(quiz.getAnswerOptions().get(i).get(j));
			}
			
			//allow student to answer question
			String input = sc.nextLine();	
			char answer = input.charAt(0);
			validateAnswer(answer);
			studentAnswers.add(answer);
			
			System.out.println();
		}
		
		QuizMarker marker = new QuizMarker(quiz.getAnswers(), studentAnswers);
		System.out.println("correct: " + marker.correctAnswers());
		System.out.println("incorrect: " + marker.incorrectAnswers());
		System.out.println("incorrect questions: " + marker.questionsMissed());
	}
	
	
	private static void validateAnswer(char answer)
	{
		Scanner sc = new Scanner(System.in);
		char val1 = 'a';
		char val2 = 'b';
		char val3 = 'c';
		char val4 = 'd';

		while(answer != val1 && answer != val2 && answer != val3 && answer != val4)
		{
			System.out.print("That is not a valid answer.");
			System.out.println(" Please re-enter your answer: ");
			String input = sc.nextLine();	
			answer = input.charAt(0);
		}
		
	}

}
