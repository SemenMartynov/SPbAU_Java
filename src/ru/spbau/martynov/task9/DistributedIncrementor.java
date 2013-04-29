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

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Semen Martynov
 * 
 *         Task management service.
 */
public class DistributedIncrementor {

	/**
	 * Queue of tasks for execution.
	 */
	private final Queue<Task> taskQueue = new LinkedList<Task>();

	/**
	 * Constructor. Creates required quantity of worker threads.
	 * 
	 * @param workersQuantity
	 *            quantity of worker threads.
	 */
	public DistributedIncrementor(int workersQuantity) {
		for (int i = 0; i < workersQuantity; ++i) {
			Worker worker = new Worker(taskQueue);
			Thread workerThread = new Thread(worker, "worker_thread_" + i);
			workerThread.setDaemon(true);
			workerThread.start();
		}
	}

	/**
	 * Receives number, adds it in queue of tasks, returns result of execution.
	 * 
	 * @param i
	 *            number for increase.
	 * @return result of increase.
	 * @throws InterruptedException
	 *             if the current thread is not the owner of this object's
	 *             monitor.
	 */
	public int increment(int i) throws InterruptedException {
		final Task task = new Task(i);

		synchronized (taskQueue) {
			taskQueue.add(task);
			taskQueue.notify(); // notify worker, that there is new task
		}

		synchronized (task) {
			while (!task.isReady())
				task.wait(); // wait until task is ready
		}

		return task.getValue();
	}

}
