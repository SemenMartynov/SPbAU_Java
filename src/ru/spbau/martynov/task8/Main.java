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
package ru.spbau.martynov.task8;

import java.util.Random;

import ru.spbau.martynov.task8.array.ArrayCalculator;
import ru.spbau.martynov.task8.philosopher.Philosopher;
import ru.spbau.martynov.task8.philosopher.Table;

/**
 * 
 * @author Semen Martynov
 * 
 *         Principal file of the program.
 */
public class Main {

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            The first argument specifies what problem needs to be solved:
	 *            philosopher or array.
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Too few parameters");
			System.exit(1);
		}

		if (args[0].equals("philosopher")) {
			try {
				// Number of philosophers
				int n = 5;

				Table table = new Table(n);
				for (int i = 0; i < n; ++i) {
					Philosopher philosopher = new Philosopher(i, table);
					Thread philosopherThread = new Thread(philosopher,
							"philosopher's_thread_" + i);
					philosopherThread.start();
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} else if (args[0].equals("array")) {
			try {
				// Array size
				int arraySize = 12;
				// Quantity of master threads
				int masterNumber = 4;
				// Quantity of worker threads
				int workerNumber = 3;

				// Fill an array
				System.out.print("Array: ");
				Random random = new Random();
				int[] array = new int[arraySize];
				for (int i = 0; i < arraySize; ++i) {
					array[i] = random.nextInt(100);
					System.out.print(array[i] + " ");
				}
				System.out.print("\n");

				ArrayCalculator arrayCalculator = new ArrayCalculator(
						masterNumber, workerNumber, array);
				System.out.println("Reusult: " + arrayCalculator.getSum());

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
