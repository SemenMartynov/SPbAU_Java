package ru.spbau.martynov.task2;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Semen A Martynov, 13 Feb 2013
 * 
 *         The class checks compliance between file name and the regular
 *         expression line.
 */
public class PatternFilter implements FileFilter {
	/**
	 * Constructor from the regular expression line.
	 * 
	 * @param filterConditions
	 *            the regular expression line (see
	 *            http://docs.oracle.com/javase/
	 *            7/docs/api/java/util/regex/Pattern.html).
	 * @throws PatternSyntaxException
	 *             If the expression's syntax is invalid.
	 */
	public PatternFilter(String filterConditions) throws PatternSyntaxException {
		filter = Pattern.compile(filterConditions);
	}

	/**
	 * Attempts to match file name against the filter.
	 * 
	 * @param file
	 *            for the matching.
	 * @return true if, and only if, the entire file's name matches filter's
	 *         pattern
	 */
	@Override
	public boolean accept(File node) {
		return filter.matcher(node.getName()).matches();
	}

	/**
	 * A regular expression, specified as a string, must first be compiled into
	 * an instance of this class.
	 */
	private final Pattern filter;
}
