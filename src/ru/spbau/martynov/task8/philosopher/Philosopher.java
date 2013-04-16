/**
 * Copyright 2013 Semen A Martynov <semen.martynov@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ru.spbau.martynov.task8.philosopher;

import java.util.Random;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class philosopher who sleeps and eats. For food, he needs to take two
 *         sticks (left and right).
 */
public class Philosopher implements Runnable {

	/**
	 * Number (name) of the philosopher.
	 */
	private int id;
	/**
	 * The table at which the philosopher sits.
	 */
	private final Table table;
	/**
	 * Left chopstick.
	 */
	private final int leftChopstickId;
	/**
	 * Right chopstick.
	 */
	private final int rightChopstickId;
	/**
	 * Random number generator (for determination time of a dream and food).
	 */
	private final Random random = new Random();

	/**
	 * Constructor
	 * 
	 * @param id
	 *            - number (name) of the philosopher.
	 * @param table
	 *            at which the philosopher sits.
	 */
	public Philosopher(int id, Table table) {
		this.id = id;
		this.table = table;
		leftChopstickId = id;
		rightChopstickId = (id == table.getSeatsNumber() - 1) ? 0 : id + 1;
	}

	/**
	 * Philosopher took left chopstick.
	 */
	private void takeLeft() {
		System.out.println("Philosopher " + id + " took the chopstick "
				+ leftChopstickId);
		table.takeChopstick(leftChopstickId);
	}

	/**
	 * Philosopher took right chopstick.
	 */
	private void takeRight() {
		System.out.println("Philosopher " + id + " took the chopstick "
				+ rightChopstickId);
		table.takeChopstick(rightChopstickId);
	}

	/**
	 * The philosopher takes away the left chopstick (anybody more can't take
	 * away this stick while the philosopher won't return to it).
	 * 
	 * @return occupied chopstick
	 */
	private Chopstick occupyLeft() {
		System.out.println("Philosopher " + id
				+ " wants to take the chopstick " + leftChopstickId);
		return table.occupyFork(leftChopstickId);
	}

	/**
	 * The philosopher takes away the right chopstick (anybody more can't take
	 * away this stick while the philosopher won't return to it).
	 * 
	 * @return occupied chopstick
	 */
	private Chopstick occupyRight() {
		System.out.println("Philosopher " + id
				+ " wants to take the chopstick " + rightChopstickId);
		return table.occupyFork(rightChopstickId);
	}

	/**
	 * The philosopher returns the right chopstick.
	 */
	private void retrieveLeft() {
		System.out.println("Philosopher " + id + " retrieves the chopstick "
				+ leftChopstickId);
		table.retrieveChopstick(leftChopstickId);
	}

	/**
	 * The philosopher returns the left chopstick.
	 */
	private void retrieveRight() {
		System.out.println("Philosopher " + id + " retrieves the chopstick "
				+ rightChopstickId);
		table.retrieveChopstick(rightChopstickId);
	}

	/**
	 * Life cycle of the philosopher (if it was succeeded to take one chopstick,
	 * he holds it until he can't take the second one).
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (occupyRight()) {
				takeRight();
				synchronized (occupyLeft()) {
					takeLeft();
					eat();
					retrieveLeft();
				}
				retrieveRight();
			}
			sleep();
		}
	}

	/**
	 * Philosopher eats (random time, depending on the number of philosophers).
	 */
	private void eat() {
		System.out.println("Philosopher " + id + " is eating");
		long sleepTime = (random.nextInt(table.getSeatsNumber()) + 1) * 4000;
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Philosopher sleeps (random time, depending on the number of
	 * philosophers).
	 */
	private void sleep() {
		System.out.println("Philosopher " + id + " is sleeping");
		long sleepTime = (random.nextInt(table.getSeatsNumber()) + 1) * 2000;
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
		System.out.println("Philosopher " + id + " woke up");
	}
}
