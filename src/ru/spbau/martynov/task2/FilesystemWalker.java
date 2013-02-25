package ru.spbau.martynov.task2;

import java.io.File;
import java.util.Arrays;

/**
 * @author Semen A Martynov, 13 Feb 2013
 * 
 * This class allows to make the recursive bypass of directory tree.
 */
public class FilesystemWalker {
	
	/**
	 * Constructor with one argument - filter. Default FilesystemPrinter will be used for display a tree.
	 * 
	 * @param filter matches file name against the regular expression.
	 */
	public FilesystemWalker(PatternFilter filter) {
		this(filter, new FilesystemPrinter());
	}

	/**
	 * Constructor with two argument.
	 * 
	 * @param filter matches file name against the regular expression.
	 * @param printer displays a directory' tree.
	 */
	public FilesystemWalker(PatternFilter filter, FilesystemPrinter printer) {
		this.filter = filter;
		this.printer = printer;
	}
	
	/**
	 * Begins bypass of directories from startPoint.
	 * 
	 * @param startPoint The root of the directory tree.
	 * @throws IllegalArgumentException If startPoint doesn't exist.
	 */
	public void start(String startPoint) throws IllegalArgumentException {
		File node = new File(startPoint);
		if (!node.exists())
			throw new IllegalArgumentException(startPoint + " doesn't exist");
		try {
		// Can we work with this file?
		if (filter.accept(node)) {
			if (node.isDirectory()) {
				printer.printDirectory(node.getName(), 0, isAccessible(node));
				start(node, 1);
			} else {
				printer.print(node.getName(), 0, isAccessible(node));
			}			
		}
		} catch (SecurityException e) {
            return;
        }
	}
	
	/**
	 * Recursive bypass of directory tree.
	 * We get a filtered list of sub elements and iterate through it. If we find a directory - go deeper, if a file - just print it.
	 * 
	 * @param node Current directory, which can contain sub directories and files.
	 * @param deep The nesting level concerning a root.
	 */
	private void start(File node, int deep) {
		try {
		
			// Get list of all elements
			File[] nodes = node.listFiles(filter);
			// If we can't get access to folder - we can't get access to sub folders
			if (nodes == null) {
				return;
			}
			// Sort directories before files, and then lexicographical sorting by names
			Arrays.sort(nodes, new LexicographicalOrder());
			
			// Let's see, what we have here
			for (File subNode : nodes) {
				// if it's a directory - we need to go deeper
				if (subNode.isDirectory()) {
					printer.printDirectory(subNode.getName(), deep, isAccessible(subNode));
					// if last element is a directory - we need a special steep for nice tree
					if (subNode == nodes[nodes.length - 1]) {
						printer.setShift(deep);
					}
					start(subNode, deep+1);
				} else {
					// For file - just print
					printer.print(subNode.getName(), deep, isAccessible(subNode));
				}
			}
		// This is not cool, but corresponding a task.
		} catch (SecurityException e) {
            return;
        }
	}	
	
	/**
	 *  Function checks a file access.
	 * 
	 * @param node the file, which is need to be checked for access.
	 * @return true if, and only if, file can be read without exceptions.
	 */
	private boolean isAccessible(File node) {
		try {
            if (!node.canRead()) {
                return false;
            }
        } catch (SecurityException e) {
        	return false;
        }
		return true;
	}
	
	/**
	 * Matches file name against the regular expression
	 */
	private PatternFilter filter;
	/**
	 * Displays a directory' tree.
	 */
	private FilesystemPrinter printer;
}
