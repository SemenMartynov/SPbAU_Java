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
 * The class defines the scope of work for the master thread, runs them, and sums their results.
 * 
 */
public class ArrayCalculator {
	
	/**
	 * An array with the results of the master threads.
	 */
	private VolatileInt[] masterResults;

	/**
	 * Constructor.
	 * Divides an array into approximately equal sections, by quantity the master threads.
	 * 
	 * @param masterNumber quantity the master threads.
	 * @param workerNumber quantity the worker threads.
	 * @param array for calculation.
	 * @throws IllegalArgumentException if array is empty
	 */
	public ArrayCalculator(int masterNumber, int workerNumber, int[] array) {
		if (array.length == 0) {
			throw new IllegalArgumentException("Array is empty");
		}
		
		masterResults = new VolatileInt[masterNumber];
		Thread masterThreads[] = new Thread[masterNumber];
		int section = (int) Math.ceil(array.length / masterNumber);

		for (int i = 0; i < masterNumber; i++) {
			masterResults[i] = new VolatileInt();
			int sectionBegin;
			int sectionEnd;

			sectionBegin = i * section;
			if (i != masterNumber - 1) {
				sectionEnd = sectionBegin + section;
			} else {
				// tail
				sectionEnd = array.length;
			}

			//System.out.println("Master " + i + ": from " + sectionBegin + " till " + sectionEnd);
			Master master = new Master(sectionBegin, sectionEnd, workerNumber,
					array, masterResults[i]);
			masterThreads[i] = new Thread(master);
			masterThreads[i].start();
		}

		try {
			for (int i = 0; i < masterNumber; i++) {
				masterThreads[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Adds the sum from all master threads.
	 * 
	 * @return sum from all master threads.
	 */
	public int getSum() {
		int arraySum = 0;
		for (int i = 0; i < masterResults.length; i++) {
			arraySum += masterResults[i].getValue();
		}
		return arraySum;
	}

}
