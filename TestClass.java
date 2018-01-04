

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TestClass
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Users users = null;
		String name = "";
		
		try{
			users = new Users();
		}
		catch(FileNotFoundException | java.io.FileNotFoundException ex){
			System.out.println("File not found.");
		}
		
		//starts the program, gets login status from user
		int choice = initialize();
		
		switch (choice)
		{
		case 1: //user is a student
			
			//checks user username and password
			name = login(choice, users);
			
			String another;
			String quizChoice = "";
			Quiz quiz = null;
			do
			{
				try 
				{
					quizChoice = chooseQuiz();
					quiz = new Quiz(quizChoice);
				} 
				catch (FileNotFoundException | IOException | EmptyLineException e) {
					e.printStackTrace();
				}
			
				// take quiz
				ArrayList<Character> studentAnswers = takeQuiz(quiz, sc);
			
				// display results
				QuizMarker marker = new QuizMarker(quiz.getAnswers(), studentAnswers);
				displayResults(marker);
			
				// write scores to file
				saveScores(marker, name, quizChoice);
				
				do 
				{
					System.out.println("\nWould you like to take another quiz? y/n");
					another = sc.nextLine();
				}while(!(another.equalsIgnoreCase("y") || another.equalsIgnoreCase("n")));
			}while (another.equalsIgnoreCase("y"));

			if (!(another.equalsIgnoreCase("y")))
			{
				System.out.println("Ending application...");
				System.out.println("Quiz bank closed.");
				System.exit(0);
			}
			break;
			
		case 2: //user is a teacher
		
			//checks user username and password
			name = login(choice, users);
			int choiceT;
			do{
				choiceT = teacherMenu();
				teacherAction(choiceT);
				
			}while(choiceT != 0);
			
			break;
		default:
			System.out.println("Incorrect Entry");
			break;
		}
		
	}
	
	
	/**
	 * The initialize method does the initialization of the quiz program.
	 * Asks the user to choose his user status (teacher or student).
	 * @return int choice The number corresponding to the user status.
	 */
	private static int initialize()
	{
		JOptionPane.showMessageDialog(null, "Initializing quiz program...Press OK to continue");
		final JPanel panel = new JPanel();
	
		final ButtonGroup group = new ButtonGroup();
		final JRadioButton student = new JRadioButton("student");
		final JRadioButton teacher = new JRadioButton("teacher");
		group.add(student);
		group.add(teacher);
		
		final JLabel message = new JLabel("Please choose your user status: ");
		panel.add(message);
		panel.add(student);
		panel.add(teacher);
		
		int choice = 0;
		
		do{
		JOptionPane.showMessageDialog(null, panel);
		
		if(student.isSelected())
		{
			choice = 1;
			break;
		}
		else if(teacher.isSelected())
		{
			choice = 2;
			break;
		}
		}while(!student.isSelected() && !teacher.isSelected());
			
		return choice;
	}
	
	/**
	 * The login method logs in a user to the system.
	 * Login is only successful if username and password provided are found to be valid.
	 * @param choice The number indicating the user type (teacher or student)
	 * @param users A User object that holds the state of the user
	 * @return name The name of the user
	 */
	public static String login(int choice, Users users)
	{
		boolean found = true;
		String name = "";
		String password = "";
		
		do
		{// checks identification
			try
			{
				found = true;
				name = JOptionPane.showInputDialog("Please enter your username: ");
				password = JOptionPane.showInputDialog("Please enter your password: ");
				if(choice == 1)
				{
					users.checkStudentPassword(name, password);
				}
				else
				{
					users.checkTeacherPassword(name, password);
				}
				
			}
			catch (IncorrectPasswordException | IncorrectUsernameException e)
			{
				System.out.println(e);
				found = false;
			}
		}while (found == false);
		
		if (choice == 1)
		{
			System.out.println("Hello " + name);
			return name;
		}
		else return null;
	}
	
	/**
	 * The teacherMenu method displays a list of options available to a teacher user.
	 * @return int choice The chosen option
	 */
	private static int teacherMenu()
	{
		Scanner sc = new Scanner(System.in);
		int choice = -1;
		
		do
		{
			System.out.println("\nChoose an option from the following: \n");
			System.out.println("1. Create new quiz");
			System.out.println("2. View quizzes in test bank");
			System.out.println("3. View student results");
			System.out.println("4. Delete quiz");
			System.out.println("5. Add new student");
			System.out.println("0. Exit application");
		
			try{
				choice = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Invalid entry. Please enter a number from 0-4: ");
				sc.next();
			}
		}while(choice < 0);
	
		return choice;
	}
	
	/**
	 * The teacherAction method reads in the teacher's choice
	 * as an int and chooses an action depending on the choice.
	 */
	private static void teacherAction(int choice) 
	{
		Scanner sc = new Scanner(System.in);
		boolean check = true;
		
		switch (choice)
		{
		case 1:	//create new quiz
			quizDirections();
			
			String quizName = "";
			String questions = "";
			int numQs = 0;
			
			System.out.println("\nEnter name of quiz: ");
			quizName = sc.nextLine();
			quizName = checkResponse(quizName);
			
			do
			{
				System.out.println("Enter number of questions: ");
				try{
					numQs = sc.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Invalid entry. Please enter a number from 1-100: ");
					sc.next();
				}
			}while(numQs < 1 || numQs > 100);

			try
			{
				NewQuiz myQuiz = new NewQuiz(quizName, numQs, 4);
				createQuiz(myQuiz);
			}
			catch(DuplicateQuizNameException e){
				System.out.println("A quiz with that name already exists.");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		case 2: //display available quizzes
			showQuizzes();
			break;
		case 3:	//display student results
			getMarks();
			break;
		case 4: //deletes quiz
			deleteQuiz();
			break;
		case 5:  //add new student
			addStudent();
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
	
	/**
	 * The addStudent method allows a teacher to add
	 * a new student user to the application.
	 */
	private static void addStudent()
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter student username: ");
		String username = scan.nextLine();
		System.out.println("Enter password: ");
		String password = scan.nextLine();
		
		try
		{
			Users users = new Users();
			users.addStudent(username, password);
		}
		catch (IOException  | FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch (DuplicateUserNameException e)
		{
			System.out.println("Username already in the system. Please try again.");
			addStudent();
		}
	}
	
	/**
	 * The createQuiz method guides a teacher in the creation of a new Quiz object.
	 * The quiz is added to a list of quizzes available to students.
	 * @param myQuiz A NewQuiz object
	 */
	private static void createQuiz(NewQuiz myQuiz) 
	{
		boolean check = true;
		String question;
		String option;
		
		Scanner sc = new Scanner(System.in);
		
		for(int i = 0; i < myQuiz.getNumQs(); i++)
		{
			
				System.out.println("Enter question " + (i+1) + ": ");
				question = sc.nextLine();
				question = checkResponse(question);
				try
				{
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
	  				do{
	  				System.out.println("Enter option " + (n+1) + ": ");
	  				option = sc.nextLine();
	  				
	  				option = checkResponse(option);
	  				}while(check == false);
	 				
	  				//if(option.equals(""))
	  				//{
	  					//throw new EmptyLineException("");
	  				//}
	  				switch(n)
	  				{
	  				case 0:
	 					options.add("A. " + option);
	 					break;
	 				case 1:
	 					options.add("B. " + option);
	 					break;
	 				case 2:
	 					options.add("C. " + option);
	 					break;
	 				case 3: 
	 					options.add("D. " + option);
	 					break;
	 				}
	 				
	 			}
	 			myQuiz.addOptions(options);
	 			}
	 			catch(EmptyLineException e){
	 				System.out.println("Cannot add empty line.");
	  			}
			char answer;
			boolean valid = false;
			
			System.out.println("Enter the character of the correct answer: ");
			
			do
			{
				answer = sc.next().charAt(0);
				valid = validateAnswer(answer);
				if(valid==true)
				{
					myQuiz.addAnswer(answer);
					break;
				}
			}while(valid==false);
			sc.nextLine();
		}
		
		System.out.println("\nYou have just created the following quiz: \n");
		myQuiz.viewQuiz();
		
		try 
		{
			myQuiz.makeNewQuiz();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The quizDirections method displays a set of instructions for the teacher creating a new quiz.
	 */
	private static void quizDirections()
	{
		System.out.println("QUIZ DIRECTIONS:");
		System.out.println("You may specify the amount of questions on the quiz.");
		System.out.println("Each question must have four possible answers.");
		System.out.println("Do not leave any options blank.");
		System.out.println("An answer is a single character that matches the correct answer option: a, b, c, or d.");
		System.out.println("A correct answer may only be ONE character long "
				+ "(do not make both A and B correct; rather make anther options be \"Both A and B\"");
	}
	
	
	public static String checkResponse(String answer)
	{
		Scanner sc = new Scanner(System.in);
		
		boolean valid = true;
		
		while (answer.isEmpty())
		{
			valid = false;
			System.out.println("Please re-enter your response: ");
			answer = sc.nextLine();
			if(!answer.isEmpty())
			{
				valid = true;
				break;
			}
		}
		
		return answer;
	}
	
	
	/**
	 * The chooseQuiz method presents a list of available quizzes that a student may choose
	 * and asks the student to choose a quiz from the list.
	 * @return String quiz The name of the quiz that the student has chosen to take.
	 */
	private static String chooseQuiz()
	{
		Scanner sc = new Scanner(System.in);
		
		int choice = 0;
		String quiz = "";
		boolean chosen = false;
		
		System.out.println("Please select a quiz: ");
		
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
	 */
	private static ArrayList<String> showQuizzes()
	{
		System.out.println("The following quizzes are available: ");
		
		QuizList quizzes = null;
		
		try {
			quizzes = new QuizList();
		} 
		catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		list = quizzes.getList();
		
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println((i+1) + ". " + list.get(i));
		}
		
		return list;
	}
	
	/**
	 * The deleteQuiz method allows a teacher to delete a quiz from the list of available quizzes.
	 */
	private static void deleteQuiz()
	{
		Scanner scan = new Scanner(System.in);
		
		QuizList quizzes;
		
		try
		{
			quizzes = new QuizList();
			String quiz = chooseQuiz();
			
			System.out.println("Are you sure you want to delete this quiz? (Y/N)");
			String response = scan.nextLine();
			
			if(response.equalsIgnoreCase("Y"))
			{
				if(quizzes.deleteQuiz(quiz))
				{
					System.out.println("Quiz deleted.");
				}
			}
			else
			{
				System.out.println("Delete aborted.");
			}
			
		}
		catch (IOException | IncorrectQuizNameException e)
		{
			System.out.println(e);
		}
		
		
	}
	

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
	private static boolean validateAnswer(char answer)
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
	 */
	private static void saveScores(QuizMarker marker, String studentName, String quizName) 
	{
		PrintWriter printWriter = null;
		
		try {
			printWriter = new PrintWriter(new FileWriter("Marks.txt",true));
		} 
		catch (IOException e){
			e.printStackTrace();
		}
		
		printWriter.append(studentName);
		printWriter.append("\n" + marker.getScore());
		printWriter.append("\n" + quizName  + "\n");
		printWriter.close();
	}

	/**
	 * The getMarks method returns a list of grades for a teacher to view.
	 * Grades can be viewed as a comprehensive list, or can be sorted by 
	 * student or by quiz.
	 */
	private static void getMarks() 
	{
		Scanner sc = new Scanner(System.in);
		
		Marks marks = null;
		
		try {
			marks = new Marks();
		} 
		catch (java.io.FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("How would you like to view quiz results?");
		
		System.out.println("1. By student");
		System.out.println("2. By quiz");
		System.out.println("3. Show all grades");
		
		int choice = sc.nextInt();
		
		switch(choice)
		{
		case 1: //show grades by student
			ArrayList<String> students = new ArrayList<String>();
			
			try{
			Users studentUsers = new Users();
			students = studentUsers.getStudents();
			}
			catch(FileNotFoundException | java.io.FileNotFoundException e)
			{
				System.out.println("File not found.");
			}
			
			for(int i = 0; i < students.size(); i++)
			{
				System.out.println((i+1) + ". " + students.get(i));
			}
			
			System.out.println("Enter the number corresponding to the student you would like to view: ");
			
			int stu = sc.nextInt();
			String stud = students.get(stu - 1);
			if(marks.getNames().contains(stud))
			{
				ArrayList<Integer> grades = new ArrayList<Integer>();
				for(int i = 0; i < marks.getNames().size(); i++)
				{
					if(marks.getNames().get(i).equals(stud))
					{
						grades.add(marks.getGrades().get(i));
					}
				}
				
				System.out.println(grades);
				
				//calculate student average
				double points = 0;
				
				for(Integer g : grades)
				{
					points += g;
				}
				double average = points / grades.size();
				System.out.println("Average: " + average);
			}
			else
			{
				System.out.println("There are currently no grades for the selected student.");
			}
			break;
			
		case 2: //show grades by quiz
			ArrayList<String> quizzes = showQuizzes();
			
			System.out.println("Enter the number corresponding to the quiz you would like to view: ");
			int quiz = sc.nextInt();
			String qz = quizzes.get(quiz - 1);
			
			if(marks.getQuizzes().contains(qz))
			{
				ArrayList<Integer> grades = new ArrayList<Integer>();
				for(int i = 0; i < marks.getQuizzes().size(); i++)
				{
					if(marks.getQuizzes().get(i).equals(qz))
					{
						grades.add(marks.getGrades().get(i));
					}
				}
				
				System.out.println(grades);
				
				//calculate student average
				double points = 0;
				
				for(Integer g : grades)
				{
					points += g;
				}
				double average = points / grades.size();
				System.out.println("Average: " + average);
			}
			else
			{
				System.out.println("There are currently no grades for the selected quiz.");
			}
			break;
		case 3: //show all grades
			for (int i =0; i < marks.getGrades().size(); i++)
			{
				System.out.println(marks.getNames().get(i)+ ": " 
						+ marks.getQuizzes() .get(i) +" " + marks.getGrades().get(i));
			}
			break;
		default:
			System.out.println("Incorrect Entry");
			break;
		}
	
	}
}
