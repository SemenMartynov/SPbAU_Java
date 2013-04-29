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

import java.util.Queue;

/**
 * @author Semen Martynov
 * 
 */
public class Worker implements Runnable {
	/**
	 * Queue of tasks for execution.
	 */
	private final Queue<Task> taskQueue;

	/**
	 * Constructor.
	 * 
	 * @param taskQueue
	 *            Queue of tasks for execution.
	 */
	public Worker(Queue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}

	/**
	 * Thread waits for the task, then he pulls out a task from the queue and
	 * executes it.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Task task;
				synchronized (taskQueue) {
					while (taskQueue.isEmpty())
						taskQueue.wait();
					task = taskQueue.poll();
				}

				// NB: the waiting time added for tasks balancing!!
				synchronized (task) {
					try {
						Thread.sleep(100L);
					} finally {
						// We can not lose the task.
						task.execute();
						task.notify();
					}
				}
			} catch (InterruptedException e) {
				// Complete thread.
				break;
			}
		}
	}
}
