package ru.spbau.martynov.task4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Semen A Martynov, 11 Mar 2013
 * 
 *         Class for the recursive bypass of directories, and files archiving in
 *         ZIP archive.
 */
public abstract class Event {

	/**
	 * The abstract method of check of readiness of object.
	 * 
	 * @return Result of object readiness check.
	 */
	public abstract boolean ready();

	/**
	 * Method of subscribers list bypass.
	 */
	public void fireEvent() {
		if (ready()) {
			for (ActionListener actionListener : actionListeners) {
				actionListener.performAction();
			}
		}
	}

	/**
	 * Method of adding the subscriber to the list.
	 * 
	 * @param actionListener
	 *            The subscriber for adding.
	 */
	public void addListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}

	/**
	 * List of subscribers.
	 */
	private List<ActionListener> actionListeners = new ArrayList<ActionListener>();
}
