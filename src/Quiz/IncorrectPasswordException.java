package Quiz;


public class IncorrectPasswordException extends Exception
{

	public IncorrectPasswordException()
	{
		super();
	}

	public IncorrectPasswordException(String message)
	{
		super(message);
	}

}