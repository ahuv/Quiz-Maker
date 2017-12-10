package Quiz;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;

public class Users
{
	private ArrayList<Person> students;
	private ArrayList<Person> teachers;
	Scanner studentScan;
	Scanner teacherScan;
	
	public Users() throws FileNotFoundException
	{
		students = new ArrayList<Person>();
		teachers = new ArrayList<Person>();

		readStudentFile();
		readTeacherFile();
	}
	
	private void readStudentFile() throws FileNotFoundException
	{
		FileReader studentFile = new FileReader("StudentUserNames.txt");
		studentScan = new Scanner(studentFile);
		
		while(studentScan.hasNext())
		{
			Person student = new Person(studentScan.next(),studentScan.next());
			students.add(student);
		}
	}
	
	private void readTeacherFile() throws FileNotFoundException
	{
		FileReader teacherFile = new FileReader("TeacherUserNames.txt");
		teacherScan = new Scanner(teacherFile);
		
		while(teacherScan.hasNext())
		{
			Person teacher = new Person(teacherScan.nextLine(),teacherScan.nextLine());
			teachers.add(teacher);
		}
		
	}
	
	public boolean checkStudentPassword(String name, String password) throws IncorrectPasswordException, IncorrectUsernameException
	{
		for (Person p : students)
		{
			if (p.getName().equals(name))
			{
				if (!p.getPassword().equals(password))
				{
					throw new IncorrectPasswordException("Incorrect Password.");
				}
				return true;
			}
		}
		
		throw new IncorrectUsernameException("Incorrect Username.");
	}
	
	public boolean checkTeacherPassword(String name, String password) throws IncorrectPasswordException, IncorrectUsernameException
	{
		for (Person p : teachers)
		{
			if (p.getName().equals(name))
			{
				if (!p.getPassword().equals(password))
				{
					throw new IncorrectPasswordException("Incorrect Password.");
				}
				return true;
			}
		}
		
		throw new IncorrectUsernameException("Incorrect Username.");
	}

	public void addStudent(String username, String password) throws IOException, DuplicateUserNameException
	{
		
		
		FileReader studentFile = new FileReader("StudentUserNames.txt");
		studentScan = new Scanner(studentFile);
		
		while(studentScan.hasNext())
		{
			if(studentScan.nextLine().equalsIgnoreCase(username))
			{
				throw new DuplicateUserNameException();
			}
			studentScan.nextLine();
		}
		
		PrintWriter printWriter = new PrintWriter(new FileWriter("StudentUserNames.txt", true));
		
		printWriter.append(username + "\n");
		printWriter.append(password + "\n");
		printWriter.close();
	}
	
}
