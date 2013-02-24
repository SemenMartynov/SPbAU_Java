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
package ru.spbau.martynov.task1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	/**
	 * Entry point
	 * 
	 * @param args
	 *            the first one parameter specifies source file, the second -
	 *            receiver file (in case of its absence the result is output in
	 *            the console).
	 */
	public static void main(String[] args) {
		// Do we have a source file?
		if (args.length > 0) {

			FileMessageReader messageReader = null;
			MessageWriter messageWriter = null;

			try {
				messageReader = new FileMessageReader(args[0]);

				if (args.length > 1) {
					messageWriter = new CompressingMessageWriter(new FileMessageWriter(args[1]));
				} else {
					messageWriter = new CompressingMessageWriter(new ConsoleMessageWriter());
				}
								
				// Let's get this party started!
				Message message = null;
				while ((message = messageReader.getMessage()) != null) {
					messageWriter.writeMessage(message);
				}

			} catch (FileNotFoundException e) {
				// Only messageReader could throw FileNotFoundException.
				System.err.println("Couldn't find file: " + args[0]);
			} catch (IOException e) {
				System.err.println("Strange IOException happened during file processing. Message: " + e.getMessage());
			} catch (IllegalMessageFormatException e) {
				System.err.println("Error: " + e.getMessage());
			} finally {
				try {
					if (messageReader != null) {
						messageReader.close();
					}
				} catch (IOException e) {
					System.err.println("Strange IOException happened during closing file. Message: " + e.getMessage());
				}
				try {
					if (messageWriter != null) {
						// The wrapper will close a resource by itself.
						messageWriter.close();
					}
				} catch (IOException e) {
					System.err.println("Strange IOException happened during closing file. Message: " + e.getMessage());
				}
			}
		} else {
			System.out.println("Too few parameters");
			System.exit(1);
		}
	}
}
