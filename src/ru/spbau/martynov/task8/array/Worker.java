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
package ru.spbau.martynov.task8.array;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class performs addition on the array's scope.
 * 
 */
public class Worker implements Runnable {
	/**
	 * Array's scope beginning.
	 */
	private final int subSectionBegin;
	/**
	 * Array's scope ending.
	 */
	private final int subSectionEnd;
	/**
	 * Array for calculation.
	 */
	private final int[] array;
	/**
	 * sum of all elements in the scope.
	 */
	private VolatileInt result;

	/**
	 * Constructor
	 * 
	 * @param subSectionBegin
	 *            array's scope beginning.
	 * @param subSectionEnd
	 *            array's scope ending.
	 * @param array
	 *            for calculation.
	 * @param result
	 *            sum of all elements in the scope.
	 */
	public Worker(int subSectionBegin, int subSectionEnd, int[] array,
			VolatileInt result) {
		this.subSectionBegin = subSectionBegin;
		this.subSectionEnd = subSectionEnd;
		this.array = array;
		this.result = result;
	}

	/**
	 * Addition on the array's scope.
	 */
	@Override
	public void run() {
		for (int i = subSectionBegin; i < subSectionEnd; i++) {
			result.add(array[i]);
		}

	}

}
