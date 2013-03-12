package ru.spbau.martynov.task4;

import java.util.Random;

/**
 * @author Semen A Martynov, 11 Mar 2013
 * 
 *         Class with the subscribers notified by random conditions.
 */
public class RandomEvent extends Event {

	/**
	 * The method checks random conditions for subscribers notify.
	 */
	@Override
	public boolean ready() {
		if (random.nextInt() % 2 != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Random number generator.
	 */
	private Random random = new Random();

}
