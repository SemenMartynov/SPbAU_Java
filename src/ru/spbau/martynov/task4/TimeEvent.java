package ru.spbau.martynov.task4;

/**
 * @author Semen A Martynov, 11 Mar 2013
 * 
 *         Class with the subscribers notified by time.
 */
public class TimeEvent extends Event {

	/**
	 * The method checks, whether passed enough time from the last event.
	 */
	@Override
	public boolean ready() {
		if ((System.currentTimeMillis() - lastCheck) < steep) {
			return false;
		} else {
			lastCheck = System.currentTimeMillis();
			return true;
		}
	}

	/**
	 * Time between annunciator about readiness.
	 */
	private long steep = 10 * 1000;
	/**
	 * Time of last annunciator about readiness.
	 */
	private long lastCheck = System.currentTimeMillis();
}
