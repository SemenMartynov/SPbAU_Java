package ru.spbau.martynov.task2;

import java.io.File;
import java.util.Comparator;

/**
 * @author Semen A Martynov, 13 Feb 2013
 * 
 *         The class is used for division elements of the directory into two
 *         groups (files and directories) and sorting of each group in
 *         lexicographical order {@see FilesystemWalker}.
 */
public class LexicographicalOrder implements Comparator<File> {
	/**
	 * Compares the two files. At the beginning, objects are compared by type
	 * (directory or file) and, if both objects are of the same type, file
	 * objects are compared.
	 * 
	 * @param lhs
	 *            The first file to compare.
	 * @param rhs
	 *            The second file to compare.
	 * @return positive number if the first object is not a directory, but the
	 *         second is; negative number otherwise. If both objects in the same
	 *         type - result of name comparing.
	 * @throws If
	 *             a security manager denies read access to the file
	 */
	@Override
	public int compare(final File lhs, final File rhs) throws SecurityException {
		if (lhs.isDirectory() && !rhs.isDirectory()) {
			return -1;
		} else if (!lhs.isDirectory() && rhs.isDirectory()) {
			return 1;
		} else {
			return lhs.getName().compareTo(rhs.getName());
		}
	}
};