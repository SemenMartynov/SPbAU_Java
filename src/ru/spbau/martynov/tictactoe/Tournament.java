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
package ru.spbau.martynov.tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Semen Martynov
 * 
 *         The class loads files with artificial intelligence, arranges
 *         competition, and outputs result.
 * 
 */
public class Tournament<T extends Game> extends ClassLoader implements
		Iterable<T> {

	/**
	 * Iterator implementation for pass on games.
	 * 
	 * @return iterator of game.
	 */
	@Override
	public Iterator<T> iterator() {
		Iterator<T> iterator = games.iterator();
		return iterator;
	}

	/**
	 * Constructor. Creates game objects.
	 * 
	 * @param pathToClasses
	 *            - path to the folder with class-files.
	 * @param gameClass
	 *            - Game class.
	 * @param args
	 *            - Array of parameters for game objects creation.
	 * @throws FileNotFoundException
	 *             - if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             - If the first byte cannot be read for any reason other than
	 *             end of file, or if the input stream has been closed, or if
	 *             some other I/O error occurs.
	 * @throws InstantiationException
	 *             - if the class that declares the underlying constructor
	 *             represents an abstract class.
	 * @throws IllegalAccessException
	 *             - if this Constructor object is enforcing Java language
	 *             access control and the underlying constructor is
	 *             inaccessible.
	 * @throws IllegalArgumentException
	 *             - if the number of actual and formal parameters differ; if an
	 *             unwrapping conversion for primitive arguments fails; or if,
	 *             after possible unwrapping, a parameter value cannot be
	 *             converted to the corresponding formal parameter type by a
	 *             method invocation conversion; if this constructor pertains to
	 *             an enum type.
	 * @throws InvocationTargetException
	 *             - if the underlying constructor throws an exception.
	 * @throws NoSuchMethodException
	 *             - if a matching method is not found.
	 * @throws SecurityException
	 *             - If a security manager exists and its method denies read
	 *             access to the file or directory.
	 */
	public Tournament(String pathToClasses, Class<T> gameClass, Object[] args)
			throws FileNotFoundException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		fieldSize = (int) args[0];
		numberInRow = (int) args[1];

		aiXPlayers = new LinkedList<>();
		aiOPlayers = new LinkedList<>();
		games = new LinkedList<>();

		if (fieldSize < numberInRow) {
			throw new IllegalArgumentException("Field is too small.");
		}

		File classesDirectory = new File(pathToClasses);
		if (!classesDirectory.exists())
			throw new IllegalArgumentException(pathToClasses + " doesn't exist");
		if (!classesDirectory.canRead()) {
			throw new SecurityException("Can't read from " + pathToClasses);
		}

		File[] aiClasses = classesDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".class");
			}
		});

		if (aiClasses.length == 0) {
			throw new RuntimeException(pathToClasses + " is empty");
		}

		for (File aiClass : aiClasses) {
			if (!aiClass.isDirectory() && aiClass.canRead()) {
				try {

					byte classFromFS[] = fetchClassFromFs(aiClass);
					Class<?> clazz = defineClass(null, classFromFS, 0,
							classFromFS.length);

					Method[] allMethods = clazz.getDeclaredMethods();
					for (Method mtd : allMethods) {
						String mName = mtd.getName();
						if (mName == "getGameType") {
							Player aiX = (Player) clazz.getConstructor(
									Integer.TYPE, Integer.TYPE, Boolean.TYPE)
									.newInstance(fieldSize, numberInRow, true);

							Player aiO = (Player) clazz.getConstructor(
									Integer.TYPE, Integer.TYPE, Boolean.TYPE)
									.newInstance(fieldSize, numberInRow, false);

							if (aiX.getGameType() == XOGame.class) {
								aiXPlayers.add(aiX);
								aiOPlayers.add(aiO);
							}
						}
					}
				} catch (SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException e) {
					// DO NOTHING!! Just go to the next file!
					e.printStackTrace(System.err);
				}
			}
		}

		for (Player aiX : aiXPlayers) {
			for (Player aiO : aiOPlayers) {
				games.add((T) gameClass.getConstructor(Integer.TYPE,
						Integer.TYPE, Player.class, Player.class).newInstance(
						fieldSize, numberInRow, aiX, aiO));

				games.add((T) gameClass.getConstructor(Integer.TYPE,
						Integer.TYPE, Player.class, Player.class).newInstance(
						fieldSize, numberInRow, aiO, aiX));
			}
		}
	}

	/**
	 * The function loops through all the games, collects the results and
	 * displays them in the table.
	 * 
	 * @return - String with results.
	 */
	public String getResults() {
		StringBuilder result = new StringBuilder();

		result.append('\t');
		for (int i = 0; i != aiXPlayers.size(); i++) {
			result.append(aiXPlayers.get(i).getName());
			result.append('\t');
		}
		result.append('\n');

		int gameNumber = 0;
		for (Player aiX : aiXPlayers) {
			result.append(aiX.getName());
			result.append('\t');
			for (int i = 0; i != aiOPlayers.size(); i++) {
				int score = 0;

				if (games.get(gameNumber++).getWinner() == aiX.getToken()) {
					score++;
				}
				if (games.get(gameNumber++).getWinner() == aiX.getToken()) {
					score++;
				}
				result.append(score);
				result.append('\t');
			}
			result.append('\n');
		}
		return result.toString();
	}

	/**
	 * Loads a class from a file on your hard drive.
	 * 
	 * @param aiClass
	 *            - The structure of the file, which will be loaded.
	 * @return An array of readed bytes.
	 * @throws FileNotFoundException
	 *             - if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             - If the first byte cannot be read for any reason other than
	 *             end of file, or if the input stream has been closed, or if
	 *             some other I/O error occurs.
	 */
	private byte[] fetchClassFromFs(File aiClass) throws FileNotFoundException,
			IOException {
		InputStream is = null;

		try {
			is = new FileInputStream(aiClass);

			// Get the size of the file
			long length = aiClass.length();

			if (length > Integer.MAX_VALUE) {
				throw new RuntimeException("File " + aiClass.getName()
						+ " is too large");
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ aiClass.getName());
			}

			return bytes;
		} finally {
			// Close the input stream and return bytes
			if (is != null) {
				is.close();
			}
		}

	}

	/**
	 * List of played games.
	 */
	private List<T> games;
	/**
	 * The list of players that use X
	 */
	private List<Player> aiXPlayers;
	/**
	 * The list of players that use O
	 */
	private List<Player> aiOPlayers;
	/**
	 * The length of any side of the field
	 */
	private final int fieldSize;
	/**
	 * The number of matches needed to win.
	 */
	private final int numberInRow;
}
