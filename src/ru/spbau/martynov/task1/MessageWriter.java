package ru.spbau.martynov.task1;

import java.io.IOException;

/**
 * @author Semen A Martynov, 10 Feb 2013 23:22
 * 
 *         The interface containing a method for the message output.
 */
public interface MessageWriter {

	/**
	 * Function for a message output.
	 * 
	 * @param message
	 *            The message which is required to be displayed.
	 * @throws IOException
	 *             the stream is closed or another IOException occurs.
	 */
	public void writeMessage(Message message) throws IOException;

	/**
	 * Function for release of resources.
	 * 
	 * @throws IOException
	 *             the stream is closed or another IOException occurs.
	 */
	public void close() throws IOException;
}
