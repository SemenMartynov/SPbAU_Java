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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Semen Martynov
 * 
 *         Table with chopsticks, which can be taken by the philosophers.
 */
public class Table {
	/**
	 * List with chopsticks.
	 */
	private List<Chopstick> chopsticks;
	/**
	 * Number of seats.
	 */
	private Integer seats;

	/**
	 * Constructor
	 * 
	 * @param seats
	 *            number of seats.
	 * @throws IllegalArgumentException
	 *             if seats is null.
	 */
	public Table(int seats) {
		if (seats == 0) {
			throw new IllegalArgumentException(
					"Can't create table with 0 seats.");
		}

		chopsticks = new ArrayList<Chopstick>();
		for (int i = 0; i < seats; i++) {
			chopsticks.add(new Chopstick());
		}
		this.seats = seats;
	}

	/**
	 * Returns the number of seats.
	 * 
	 * @return the number of seats.
	 */
	public Integer getSeatsNumber() {
		return seats;
	}

	/**
	 * Someone takes away the chopstick (no one else can take it until it isn't
	 * retrieved).
	 * 
	 * @param id
	 *            chopstick
	 * @return occupied chopstick
	 */
	public Chopstick occupyFork(int id) {
		return chopsticks.get(id);
	}

	/**
	 * Someone took the chopstick.
	 * 
	 * @param id
	 *            chopstick
	 */
	public void takeChopstick(int id) {
		chopsticks.get(id).take();
	}

	/**
	 * Someone returns the chopstick.
	 * 
	 * @param id
	 *            chopstick
	 */
	public void retrieveChopstick(int id) {
		chopsticks.get(id).retrieve();
	}
}
