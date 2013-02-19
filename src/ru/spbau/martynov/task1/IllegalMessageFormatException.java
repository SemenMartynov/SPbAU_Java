package ru.spbau.martynov.task1;

/**
 * @author Semen A Martynov, 10 Feb 2013 23:16
 * 
 *         The exception arises when unexpectedly reached the end of the file
 *         (or other syntax error).
 */
public class IllegalMessageFormatException extends Exception {

	/**
	 * The serialization identificator.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public IllegalMessageFormatException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause of exception.
	 */
	public IllegalMessageFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
