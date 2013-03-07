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
package ru.spbau.martynov.task3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;

/**
 * ZIP archiving implementation on Java.
 */
public class Main {

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            The first parameter defines the task - archiving, or
	 *            extraction from archive. If archiving, the second parameter
	 *            defines a name of archive where files will be placed, and all
	 *            subsequent parameters - folders and files for adding in
	 *            archive. If extraction from archive, the second parameter
	 *            defines a name of archive from which files will be derived.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Too few parameters");
			System.exit(1);
		}
		// compression
		if (args[0].equals("compress")) {
			if (args.length < 3) {
				System.out.println("Nothing compressing");
				System.exit(1);
			}
			Compressor compressor = null;
			try {
				compressor = new Compressor(args[1]);
				for (int i = 2; i != args.length; i++) {
					compressor.add(args[i]);
				}
			} catch (FileNotFoundException | SecurityException e) {
				System.out.println("Can't create " + args[1]);
				e.printStackTrace(System.err);
			} catch (ZipException e) {
				System.out.println("ZIP format error has occurred");
				e.printStackTrace(System.err);
			} catch (IOException e) {
				System.out.println("Strange IOException has occurred");
				e.printStackTrace(System.err);
			} finally {
				if (compressor != null) {
					try {
						compressor.close();
					} catch (IOException e) {
						System.out.println("Strange IOException has occurred");
						e.printStackTrace(System.err);
					}
				}
			}
			// Extraction from archive
		} else if (args[0].equals("decompress")) {
			Decompressor decompressor = null;
			try {
				decompressor = new Decompressor(args[1]);
				decompressor.get();
			} catch (SecurityException e) {
				System.out.println("Can't open " + args[1]);
				e.printStackTrace(System.err);
			} catch (ZipException e) {
				System.out.println("ZIP format error has occurred");
				e.printStackTrace(System.err);
			} catch (IOException e) {
				System.out.println("Strange IOException has occurred");
				e.printStackTrace(System.err);
			} finally {
				if (decompressor != null) {
					try {
						decompressor.close();
					} catch (IOException e) {
						System.out.println("Strange IOException occurred");
						e.printStackTrace(System.err);
					}
				}
			}
		}
	}
}