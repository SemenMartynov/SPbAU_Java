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
package ru.spbau.martynov.task9;

/**
 * @author Semen Martynov
 * 
 *         Class of complex computing task: to increase the vested number by
 *         one.
 */
public class Task {
	/**
	 * Variable value shows that computation was executed.
	 */
	private boolean ready = false;
	/**
	 * Number which is required to be increased.
	 */
	private int value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            for increase.
	 */
	public Task(int value) {
		this.value = value;
	}

	/**
	 * Execute calculations.
	 */
	public void execute() {
		value++;
		ready = true;
	}

	/**
	 * Returns the value of the stored number.
	 * 
	 * @return value of the stored number.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns true if computation was executed, and false if not.
	 * 
	 * @return true if computation was executed, and false if not.
	 */
	public boolean isReady() {
		return ready;
	}

}
