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
package ru.spbau.martynov.task7;

/**
 * @author Semen Martynov
 * 
 *         Principal file of the program.
 */
public class Main {

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            - The first parameter is responsible for operation
	 *            (serialization/deserialization). The second parameter
	 *            [optionally] defines the file.
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Too few parameters");
			System.exit(1);
		}

		String filename;
		if (args.length < 2 && !args[1].isEmpty()) {
			filename = args[1];
		} else {
			filename = "student.properties";
		}

		if (args[0].equals("serialize")) {
			serializeStudent(filename);
		} else if (args[0].equals("deserialize")) {
			deserializeStudent(filename);
		}
	}

	/**
	 * Execute serialization.
	 * 
	 * @param filename
	 *            - The file in which the class will be saved.
	 */
	private static void serializeStudent(String filename) {
		try {
			ReflectionSerializer reflectionSerializer = new ReflectionSerializer();
			Student student = new Student();

			student.setName("Ivan");
			student.setSurname("Ivanov");
			student.setAge(20);
			student.setAvgGrade(3.14);

			reflectionSerializer.serialize(filename, student);
		} catch (SerializationException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Execute deserialization.
	 * 
	 * @param filename
	 *            - The file in which the class will be loaded and saved again.
	 */
	private static void deserializeStudent(String filename) {
		try {
			ReflectionDeSerializer reflectionDeSerializer = new ReflectionDeSerializer();
			ReflectionSerializer reflectionSerializer = new ReflectionSerializer();
			Student student;

			student = reflectionDeSerializer.deserialize(filename,
					Student.class);
			student.setAvgGrade((student.getAvgGrade() < 4.0) ? student
					.getAvgGrade() + 1.0 : 5.0);

			reflectionSerializer.serialize(filename, student);
		} catch (SerializationException e) {
			System.out.println(e.getMessage());
		}
	}

}
