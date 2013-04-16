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
 *         * The class defines the scope of work for the worker thread, runs
 *         them, and sums their results.
 */
public class Master implements Runnable {
	/**
	 * Beginning of array's section.
	 */
	private final int sectionBegin;
	/**
	 * Ending of array's section
	 */
	private final int sectionEnd;

	/**
	 * Quantity the worker threads.
	 */
	private final int workerNumber;
	/**
	 * Array for calculation.
	 */
	private final int[] array;

	/**
	 * sum from all worker threads
	 */
	private VolatileInt result;

	/**
	 * Constructor
	 * 
	 * @param sectionBegin
	 *            beginning of array's section.
	 * @param sectionEnd
	 *            ending of array's section.
	 * @param workerNumber
	 *            quantity the worker threads.
	 * @param array
	 *            array for calculation.
	 * @param result
	 *            sum from all worker threads.
	 */
	public Master(int sectionBegin, int sectionEnd, int workerNumber,
			int[] array, VolatileInt result) {
		this.sectionBegin = sectionBegin;
		this.sectionEnd = sectionEnd;
		this.workerNumber = workerNumber;
		this.array = array;
		this.result = result;
	}

	/**
	 * Divides an array into approximately equal sections, by quantity the
	 * worker threads; creates worker threads, runs them, and sums their
	 * results.
	 */
	@Override
	public void run() {
		VolatileInt[] workerResults = new VolatileInt[workerNumber];
		Thread workerThreads[] = new Thread[workerNumber];
		int section = (int) Math.ceil((sectionEnd - sectionBegin)
				/ workerNumber);

		for (int i = 0; i < workerNumber; i++) {
			workerResults[i] = new VolatileInt();
			int subSectionBegin;
			int subSectionEnd;

			subSectionBegin = sectionBegin + i * section;
			if (i != workerNumber - 1) {
				subSectionEnd = subSectionBegin + section;
			} else {
				// tail
				subSectionEnd = sectionEnd;
			}

			// System.out.println("  Worker " + (sectionBegin + i) + ": from " +
			// subSectionBegin + " till " + subSectionEnd);
			Worker worker = new Worker(subSectionBegin, subSectionEnd, array,
					workerResults[i]);
			workerThreads[i] = new Thread(worker);
			workerThreads[i].start();
		}

		try {
			for (int i = 0; i < workerNumber; i++) {
				workerThreads[i].join();
				result.add(workerResults[i].getValue());
			}
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
	}

}
