package ru.spbau.martynov.task1;

import java.io.IOException;

/**
 * @author Semen A Martynov, 10 Feb 2013 23:28
 * 
 *         Class for a message output to the console. Each message, and every
 *         line in the message is numbered.
 */
public class ConsoleMessageWriter implements MessageWriter {

	/**
	 * The constructor without parameters
	 */
	public ConsoleMessageWriter() {
		messagesCounter = 0;
	}

	/**
	 * Function displays messages, their numbers, and lines numbers to the
	 * console.
	 * 
	 * @param message for outputting.
	 * @throws IOException
	 *             the stream is closed or another IOException occurs.
	 */
	@Override
	public void writeMessage(Message message) throws IOException {
		System.out.println("Message " + ++messagesCounter);

		int linesCounter = 0;
		for (String messageLine : message.getLines()) {
			System.out.println(messagesCounter + "." + (++linesCounter) + ". "
					+ messageLine);
		}

	}

	/**
	 * Function for release of resources.
	 * 
	 * @throws IOException
	 *             the stream is closed or another IOException occurs.
	 */
	@Override
	public void close() throws IOException {
		// There are no occupied resources.

	}

	/**
	 * The counter of the shown messages.
	 */
	private int messagesCounter;

}
