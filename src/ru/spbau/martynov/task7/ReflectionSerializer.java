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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Semen Martynov
 * 
 *         Class for object serialization through reflection API.
 */
public class ReflectionSerializer {

	/**
	 * Function realizes object serialization in the specified file.
	 * 
	 * @param filename
	 *            - The file in which the class will be saved.
	 * @param object
	 *            - object for serialization.
	 * @throws SerializationException
	 *             - If there aren't enough information for operation; if it is
	 *             not allowed; if the required constructor or method isn't
	 *             found; if there was an error during file processing.
	 */
	public <T> void serialize(String filename, T object)
			throws SerializationException {
		if (filename.isEmpty()) {
			throw new SerializationException("File name is empty.");
		}

		if (object == null) {
			throw new SerializationException(
					"Object for serialization is null.");
		}

		Class<?> clazz = object.getClass();
		Method methods[];
		try {
			methods = clazz.getDeclaredMethods();
		} catch (SecurityException e) {
			e.printStackTrace(System.err);
			throw new SerializationException("Can get class methods: "
					+ e.getMessage());
		}

		Properties properties = new Properties();

		for (Method method : methods) {
			String methodName = method.getName();
			String prefix = "get";
			if (methodName.startsWith(prefix)) {

				String propertyKey = Character.toLowerCase(methodName
						.charAt(prefix.length()))
						+ methodName.substring(prefix.length() + 1);

				try {
					properties.setProperty(propertyKey, method.invoke(object)
							.toString());
				} catch (IllegalAccessException e) {
					e.printStackTrace(System.err);
					throw new SerializationException(methodName
							+ " is inaccessible: " + e.getMessage());
				} catch (IllegalArgumentException e) {
					e.printStackTrace(System.err);
					throw new SerializationException(
							methodName
									+ " cannot be converted to the corresponding formal parameter type (or other illegal argument problem): "
									+ e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace(System.err);
					throw new SerializationException(methodName
							+ " throws an exception: " + e.getMessage());
				}
			}
		}

		FileWriter fileWriter = null;
		try {
			try {
				fileWriter = new FileWriter(filename);
				properties.store(fileWriter, "");
			} catch (IOException e) {
				e.printStackTrace(System.err);
				throw new SerializationException(
						"Strange IOException happened during " + filename
								+ " processing: " + e.getMessage());
			}
		} finally {
			if (fileWriter != null)
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
					throw new SerializationException(
							"Strange IOException happened during " + filename
									+ " closing: " + e.getMessage());
				}
		}
	}

}
