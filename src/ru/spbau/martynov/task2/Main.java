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
package ru.spbau.martynov.task2;

/**
* File system walker.
*/
public class Main {

	/**
	 * Entry point
	 * 
	 * @param args the first argument contains a way to a directory which is a tree root.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Only one parameter expected");
			System.exit(1);
		}

		try {
			// "\\." - the dot symbol
			FilesystemWalker walker = new FilesystemWalker(new PatternFilter("^[^\\.].*|$"));
			walker.start(args[0]);
		// IllegalArgumentException includes PatternSyntaxException
		} catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
	}

}
