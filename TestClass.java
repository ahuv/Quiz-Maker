

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TestClass
{
	public static void main(String[] args)
			throws FileNotFoundException, IOException, InputMismatchException, EmptyLineException
	{
		Scanner sc = new Scanner(System.in);
		Users users = new Users();
		boolean found = true;
		String name = "";
		
		int choice = initialize();
		switch (choice)
		{
		case 1: //user is a student
			do
			{
				// checks student identification
				try
				{
					found = true;
					name = JOptionPane.showInputDialog("Please enter your username: ");
					String password = JOptionPane.showInputDialog("Please enter your password: ");
					users.checkStudentPassword(name, password);
					// checkIdentification(users,sc,name);

				}
				catch (IncorrectPasswordException | IncorrectUsernameException e)
				{
					System.out.println(e);
					found = false;
				}
			}while (found == false);
			
			System.out.println("Hello " + name);
			
			String another;
			do
			{
			String quizChoice = chooseQuiz();
			Quiz quiz = new Quiz(quizChoice);
			
			// take quiz
			ArrayList<Character> studentAnswers = takeQuiz(quiz, sc);
			
			// display results
			QuizMarker marker = new QuizMarker(quiz.getAnswers(), studentAnswers);
			displayResults(marker);
			
			// writes scores to file
			saveScores(marker, name);
			
			System.out.println("\nWould you like to take another quiz?");
			another = sc.nextLine();
			}while(another.equalsIgnoreCase("y"));
			if(!(another.equalsIgnoreCase("y")))
			{
				System.out.println("Ending application...");
				System.out.println("Quiz bank closed.");
				System.exit(0);
			}
			break;
			
		case 2: //user is a teacher
			do
			{// checks teacher identification
				try
				{
					found = true;
					name = JOptionPane.showInputDialog("Please enter your username: ");
					String password = JOptionPane.showInputDialog("Please enter your password: ");
					users.checkTeacherPassword(name, password);
					// checkIdentification(users,sc,name);
				}
				catch (IncorrectPasswordException | IncorrectUsernameException e)
				{
					System.out.println(e);
					found = false;
				}
			}while (found == false);
			
			while(true){
			int choiceT = teacherMenu();
			switch (choiceT)
			{
			case 1:	//create new quiz
				quizDirections();
				System.out.println("\nEnter name of quiz: ");
				String quizName = sc.nextLine();
				//sc.nextLine();
				System.out.println("Enter number of questions: ");
				int numQs = sc.nextInt();
					//System.out.println("Enter number of answer options per question: ");
					//int numAs = sc.nextInt();
				try
				{
				NewQuiz myQuiz = new NewQuiz(quizName, numQs, 4);
				createQuiz(myQuiz);
				}
				catch(DuplicateQuizNameException e){
					System.out.println("A quiz with that name already exists.");
				}
				break;
			case 2: //displays available quizzes
				showQuizzes();
				break;
			case 3:	//displays student results
				Marks marks = new Marks();
				for (int i =0; i < marks.getGrades().size(); i++)
				{
					System.out.println(marks.getNames().get(i)+ ": " + marks.getGrades().get(i));
				}
				break;
			case 0: //exit application
				System.out.println("Ending application...");
				System.out.println("Quiz bank closed.");
				System.exit(0);
				break;
			default:
				System.out.println("Incorrect Entry");
				break;
			}
			}
		default:
			System.out.println("Incorrect Entry");
			break;
		}
		
	}
	
	
	/**
	 * Initialization of the quiz program.
	 * Asks the user to choose his user status (teacher or student).
	 * @return int choice The number corresponding to the user status.
	 */
	private static int initialize()
	{
		JOptionPane.showMessageDialog(null, "Initializing quiz program...Press OK to continue");
		final JPanel panel = new JPanel();
		final JRadioButton student = new JRadioButton("student");
		final JRadioButton teacher = new JRadioButton("teacher");
		final JLabel message = new JLabel("Please choose your user status: ");
		panel.add(message);
		panel.add(student);
		panel.add(teacher);
		
		JOptionPane.showMessageDialog(null, panel);
		
		int choice = 0;
		if(student.isSelected())
		{
			choice = 1;
		}
		else if(teacher.isSelected())
		{
			choice = 2;
		}
		
		return choice;
	}
	
	/**
	 * The teacherMenu method displays a list of options available to a teacher user.
	 * @return int choice The chosen option
	 */
	private static int teacherMenu()
	{
		Scanner sc = new Scanner(System.in);
		int choice;
		
		System.out.println("\nChoose an option from the following: \n");
		System.out.println("1. Create new quiz");
		System.out.println("2. View quizzes in test bank");
		System.out.println("3. View student results");
		System.out.println("0. Exit application");
		
		choice = sc.nextInt();
		return choice;
	}
	
	/**
	 * Guides a teacher in the creation of a new Quiz object.
	 * The quiz is added to a list of quizzes available to students.
	 * @param myQuiz A NewQuiz object
	 * @throws IOException
	 */
	private static void createQuiz(NewQuiz myQuiz) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < myQuiz.getNumQs(); i++)
		{
			sc.nextLine();
			try{
				System.out.println("Enter question " + (i+1) + ": ");
				String question = sc.nextLine();
				if(question.equals(""))
				{
					throw new EmptyLineException("");
				}
				myQuiz.addQuestion(question);
				
			}
			catch(EmptyLineException e){
				System.out.println("Cannot add empty line.");
			}
			
			try{
			int numOptions = myQuiz.getNumOptions();
			ArrayList<String> options = new ArrayList<String>(numOptions);
			for(int n = 0; n < numOptions; n++)
			{
				System.out.println("Enter option " + (n+1) + ": ");
				String option = sc.nextLine();
				if(option.equals(""))
				{
					throw new EmptyLineException("");
				}
				options.add(option);
			}
			myQuiz.addOptions(options);
			}
			catch(EmptyLineException e){
				System.out.println("Cannot add empty line.");
			}
			
			char answer;
			boolean valid = false;;
			
			while(valid==false)
			{
				System.out.println("Enter the character of the correct answer: ");
				answer = sc.next().charAt(0);
				valid = validAnswer(answer);
				if(valid==true)
				{
					myQuiz.addAnswer(answer);
					break;
				}
			};
			
		}
		System.out.println("\nYou have just created the following quiz: \n");
		myQuiz.viewQuiz();
		myQuiz.makeNewQuiz();
	}
	
	/**
	 * The quizDirections method diplays a set of instructions for the teacher creating a new quiz.
	 */
	private static void quizDirections()
	{
		System.out.println("QUIZ DIRECTIONS:");
		System.out.println("You may specify the amount of questions on the quiz.");
		System.out.println("Each question must have four possible answers.");
		System.out.println("Please precede an option by a character (Option 1 is A, 2 is B, 3 is C, 4 is D).");
		System.out.println("Do not leave any options blank.");
		System.out.println("An answer is a single character that matches the correct answer option: a, b, c, or d.");
		System.out.println("A correct answer may only be ONE character long "
				+ "(do not make both A and B correct; rather make anther options be \"Both A and B\"");
	}
	
	public static boolean validAnswer(char answer)
	{
		Scanner sc = new Scanner(System.in);
		boolean valid = true;
		
		while (answer != 'a' && answer != 'b' && answer != 'c' && answer != 'd' 
				&& answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D')
		{
			valid = false;
			System.out.print("That is not a valid answer.");
			System.out.println(" Please re-enter your answer: ");
			String input = sc.nextLine();
			answer = input.charAt(0);
		}
		
		return valid;
	}
	
	/**
	 * The chooseQuiz method presents a list of available quizzes that a student may choose
	 * and asks the student to choose a quiz from the list.
	 * @return String quiz The name of the quiz that the student has chosen to take.
	 * @throws java.io.FileNotFoundException
	 */
	private static String chooseQuiz() throws java.io.FileNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		String quiz = "";
		boolean chosen = false;
		
		System.out.println("Please select a quiz to take: ");
		
		ArrayList<String> list = showQuizzes();
		
		do{
			System.out.print("Enter the number of your quiz choice: ");
			choice = sc.nextInt();
		
			for(int i = 0; i < list.size(); i++)
			{
				if(i == choice-1)
				{
					quiz = list.get(i).toString();
					chosen = true;
				}
			}
		}while(chosen == false);
		
		return quiz;
	}
	
	/**
	 * The showQuizzes method displays a list of quizzes currently available for students to take.
	 * @return list An arraylist of quiz names
	 * @throws java.io.FileNotFoundException
	 */
	private static ArrayList<String> showQuizzes() throws java.io.FileNotFoundException
	{
		System.out.println("The following quizzes are available: ");
		QuizList quizzes = new QuizList();
		ArrayList<String> list = new ArrayList<String>();
		list = quizzes.getList();
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println((i+1) + ". " + list.get(i));
		}
		return list;
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

	/**
	 * The takeQuiz method presents a quiz to the student to take.
	 * @param quiz The quiz that the student is taking
	 * @param sc The scanner object
	 * @return
	 */
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

	/**
	 * The enterAnswer method sends the student's chosen answer to validateAnswer()
	 * to check if the student's chosen answer is valid
	 * before it is stored in the ArrayList of student answers.
	 * Called from the takeQuiz() method.
	 * @param sc
	 * @return answer The validated answer
	 */
	private static Character enterAnswer(Scanner sc)
	{
		
		String input = sc.nextLine();
		char answer = input.charAt(0);
		validateAnswer(answer);
		return answer;
	}

	/**
	 * The validateAnswer method checks the student's chosen answer against possible
	 * valid answers.
	 * Called from the enterAnswer() method.
	 * @param answer The student's answer to validate
	 */
	private static void validateAnswer(char answer)
	{
		Scanner sc = new Scanner(System.in);
		char val1 = 'a';
		char val2 = 'b';
		char val3 = 'c';
		char val4 = 'd';

		while (answer != val1 && answer != val2 && answer != val3 && answer != val4 
				&& answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D')
		{
			System.out.print("That is not a valid answer.");
			System.out.println(" Please re-enter your answer: ");
			String input = sc.nextLine();
			answer = input.charAt(0);
		}

	}

	/**
	 * The displayResults method displays a student's score information for the quiz he has just completed.
	 * @param marker A QuizMarker object
	 */
	private static void displayResults(QuizMarker marker)
	{

		System.out.println("Score: " + marker.getScore());
		System.out.println("Grade: " + marker.grade());
		System.out.println("correct: " + marker.correctAnswers());
		System.out.println("incorrect: " + marker.incorrectAnswers());
		System.out.println("incorrect questions: " + marker.questionsMissed());
	}

	/**
	 * The saveScores method writes the student name and score to a file of student grades.
	 * @param marker A QuizMarker object
	 * @param studentName The name of the student
	 * @throws IOException
	 */
	private static void saveScores(QuizMarker marker, String studentName) throws IOException
	{
		PrintWriter printWriter = new PrintWriter(new FileWriter("Marks.txt",true));
		printWriter.append(studentName);
		printWriter.append("\n" + marker.getScore() + "\n");
		printWriter.close();
	}

}
