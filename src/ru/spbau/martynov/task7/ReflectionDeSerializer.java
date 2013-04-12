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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Semen Martynov
 * 
 *         Class for object deserialization through reflection API.
 */
public class ReflectionDeSerializer {

	/**
	 * Function realizes object deserialization from the specified file.
	 * 
	 * @param filename
	 *            - The file in which the class will be loaded.
	 * @param clazz
	 *            - Format of a class which is required to be deserialized.
	 * @return deserialized object.
	 * @throws SerializationException
	 *             - If there aren't enough information for operation; if it is
	 *             not allowed; if the required constructor or method isn't
	 *             found; if there was an error during file processing.
	 */
	public <T> T deserialize(String filename, Class<T> clazz)
			throws SerializationException {
		if (filename.isEmpty()) {
			throw new SerializationException("File name is empty.");
		}

		if (clazz == null) {
			throw new SerializationException(
					"Class for deserialization is null.");
		}

		T object;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace(System.err);
			throw new SerializationException(
					"Class "
							+ clazz.getName()
							+ " has no nullary constructor (or other instantiation problem): "
							+ e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace(System.err);
			throw new SerializationException("Class " + clazz.getName()
					+ " or its nullary constructor is not accessible: "
					+ e.getMessage());
		}

		Method methods[];
		try {
			methods = clazz.getDeclaredMethods();
		} catch (SecurityException e) {
			e.printStackTrace(System.err);
			throw new SerializationException("Can get class methods: "
					+ e.getMessage());
		}

		Properties properties = new Properties();
		FileReader fileReader = null;
		try {
			try {
				fileReader = new FileReader(filename);
				properties.load(fileReader);
			} catch (FileNotFoundException e) {
				e.printStackTrace(System.err);
				throw new SerializationException("File " + filename
						+ " can't be found: " + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace(System.err);
				throw new SerializationException(
						"Strange IOException happened during " + filename
								+ " processing: " + e.getMessage());
			}
		} finally {
			if (fileReader != null)
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
					throw new SerializationException(
							"Strange IOException happened during " + filename
									+ " closing: " + e.getMessage());
				}
		}

		for (Method method : methods) {
			String methodName = method.getName();
			String prefix = "set";
			if (methodName.startsWith(prefix)) {
				String propertyKey = Character.toLowerCase(methodName
						.charAt(prefix.length()))
						+ methodName.substring(prefix.length() + 1);

				String propertyVal = properties.getProperty(propertyKey);
				Class<?> propertyType = method.getParameterTypes()[0];

				try {
					method.invoke(object,
							getObjectFromProperty(propertyType, propertyVal));
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

		return object;
	}

	/**
	 * Function builds an object based on its type (class).
	 * 
	 * @param propertyType
	 *            - Object's class.
	 * @param propertyVal
	 *            - Object's value.
	 * @return - created object.
	 */
	private static Object getObjectFromProperty(Class<?> propertyType,
			String propertyVal) {
		if (propertyType == Boolean.TYPE) {
			return Boolean.parseBoolean(propertyVal);
		}
		if (propertyType == Character.TYPE) {
			return propertyVal.charAt(0);
		}
		if (propertyType == Byte.TYPE) {
			return Byte.parseByte(propertyVal);
		}
		if (propertyType == Short.TYPE) {
			return Short.parseShort(propertyVal);
		}
		if (propertyType == Integer.TYPE) {
			return Integer.parseInt(propertyVal);
		}
		if (propertyType == Long.TYPE) {
			return Long.parseLong(propertyVal);
		}
		if (propertyType == Float.TYPE) {
			return Float.parseFloat(propertyVal);
		}
		if (propertyType == Double.TYPE) {
			return Double.parseDouble(propertyVal);
		}
		return propertyVal;

	}
}
