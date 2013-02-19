package ru.spbau.martynov.task1;

import java.io.IOException;

/**
 * @author Semen A Martynov, 10 Feb 2013 23:29
 * 
 *         The class is used for combining of two messages in one. A result
 *         outputs to the console or to the file.
 */
public class CompressingMessageWriter implements MessageWriter {

	/**
	 * Constructor with the class name, for real output.
	 * 
	 * @param MessageWriter
	 *            the class used for a message output.
	 */
	public CompressingMessageWriter(MessageWriter realMessageWriter) {
		messageWriter = realMessageWriter;
	}

	/**
	 * Function compresses two messages, and then transfers to other class for
	 * an output.
	 * 
	 * @param message
	 *            for compressing and outputting.
	 * @throws IOException
	 *             the stream is closed or another IOException occurs.
	 */
	@Override
	public void writeMessage(Message message) throws IOException {
		if (messageForCompressing == null) {
			messageForCompressing = message;
		} else {
			messageForCompressing.append(message);
			messageWriter.writeMessage(messageForCompressing);
			messageForCompressing = null;
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
		if (messageForCompressing != null) {
			messageWriter.writeMessage(messageForCompressing);
		}
		messageWriter.close();
	}

	/**
	 * The class enveloped by CompressingMessageWriter.
	 */
	private MessageWriter messageWriter;

	/**
	 * The message expecting couple.
	 */
	private Message messageForCompressing;
}
