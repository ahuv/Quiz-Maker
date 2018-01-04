package Quiz;

public class IncorrectQuizNameException extends Exception
{

	public IncorrectQuizNameException()
	{
		super();
	}

	public IncorrectQuizNameException(String message)
	{
		super(message);
	}
}
