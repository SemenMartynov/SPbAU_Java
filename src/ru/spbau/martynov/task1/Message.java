package ru.spbau.martynov.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Semen A Martynov, 10 Feb 2013 22:23
 * 
 *         The class describes the message consisting of the strings.
 */
public class Message {
	
	/**
	 * Function allows to join messages, adding the new message to the current.
	 * 
	 * @param message
	 *            the message which will be added to the current.
	 */
	public void append(Message message) {
		lines.addAll(message.getLines());
	}

	/**
	 * Function allows to get access to lines of the message.
	 * 
	 * @return collection of lines.
	 */
	public List<String> getLines() {
		return Collections.unmodifiableList(lines);
	}

	/**
	 * Function for adding lines to the message.
	 * 
	 * @param messageLine
	 *            line for adding.
	 */
	public void addLine(String messageLine) {
		lines.add(messageLine);
	}

	/**
	 * Message lines list.
	 */
	private final List<String> lines = new ArrayList<>();
}
