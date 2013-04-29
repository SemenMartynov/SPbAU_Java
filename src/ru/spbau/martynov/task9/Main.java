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
 *         Distributed service to increase the number by 1.
 */
public class Main {

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            doesn't play any role.
	 */
	public static void main(String[] args) {
		/**
		 * The quantity of worker threads.
		 */
		final int workersQuantity = 5;
		/**
		 * The quantity of client threads.
		 */
		final int stupidChildrenQuantity = 5;
		/**
		 * The quantity of numbers, generated by each client thread.
		 */
		final int numbersQuantity = 10000;
		/**
		 * The lower bound for the number, generated by each client thread.
		 */
		final int numberLowerBound = 1;
		/**
		 * The upper bound for the number, generated by each client thread.
		 */
		final int numberUpperBound = 1000;

		DistributedIncrementor distributedIncrementor = new DistributedIncrementor(
				workersQuantity);

		for (int i = 0; i < stupidChildrenQuantity; ++i) {
			StupidChild stupidChild = new StupidChild(i,
					distributedIncrementor, numberLowerBound, numberUpperBound,
					numbersQuantity);
			Thread stupidChildThread = new Thread(stupidChild,
					"stupid_child_thread_" + i);
			stupidChildThread.start();
		}
	}
}
