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
package ru.spbau.martynov.task11;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Semen Martynov
 * 
 *         Browser window - allows to open and view files.
 */
@SuppressWarnings("serial")
public class BrowserWindow extends JFrame {

	/**
	 * Constructor
	 * 
	 * @param userName
	 *            - displayed in the window title/
	 */
	public BrowserWindow(String userName) {
		super("Hello, " + userName);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setMinimumSize(new Dimension(400, 200));
		closeFileAction.setEnabled(false);

		// Main menu
		setJMenuBar(new JMenuBar() {
			{
				add(new JMenu("File") {
					{
						add(new OpenFileAction());
						add(closeFileAction);
						addSeparator();
						add(new ExitAction());
					}
				});
			}
		});

		// Left panel
		Box westBox = Box.createVerticalBox();
		westBox.setBorder(new EmptyBorder(6, 6, 6, 6));
		westBox.add(new JButton(new OpenFileAction()) {
			{
				setMaximumSize(new Dimension(100, 19));
				setText("Open");
			}
		});
		westBox.add(Box.createVerticalStrut(6));
		westBox.add(new JButton(closeFileAction) {
			{
				setMaximumSize(new Dimension(100, 19));
			}
		});
		westBox.add(Box.createVerticalGlue());
		westBox.add(new JButton(new AboutAction()) {
			{
				setMaximumSize(new Dimension(100, 19));
			}
		});
		westBox.add(Box.createVerticalStrut(6));
		westBox.add(new JButton(new ExitAction()) {
			{
				setMaximumSize(new Dimension(100, 19));
			}
		});
		getContentPane().add(westBox, BorderLayout.WEST);

		// Workspace
		workspace.setBorder(new EmptyBorder(6, 0, 6, 6));
		workspace.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		getContentPane().add(workspace);
	}

	/**
	 * @author Semen Martynov Exits.
	 */
	private class ExitAction extends AbstractAction {
		/**
		 * Constructor
		 */
		public ExitAction() {
			putValue(Action.NAME, "Exit");
			putValue(Action.SHORT_DESCRIPTION, "Exit program");
		}

		/**
		 * Exits.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			System.exit(0);
		}
	}

	/**
	 * @author Semen Martynov
	 * 
	 *         Open a new file and display it on a new tab.
	 */
	private class OpenFileAction extends AbstractAction {
		/**
		 * Constructor
		 */
		OpenFileAction() {
			putValue(Action.NAME, "Open file");
			putValue(Action.SHORT_DESCRIPTION, "Open file");
		}

		/**
		 * Open a new file and display it on a new tab.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File textFile = fileChooser.getSelectedFile();
			JTextPane textPane = new JTextPane();

			try (BufferedReader reader = new BufferedReader(new FileReader(
					textFile))) {
				textPane.read(reader, null);
				workspace.addTab(fileChooser.getName(textFile),
						new JScrollPane(textPane));
				workspace.setSelectedIndex(workspace.getTabCount() - 1);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"File not found", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Can't read file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @author Semen Martynov
	 * 
	 *         Closes the tab with the file. If there are no more tabs open,
	 *         close operation is not available.
	 */
	private class CloseFileAction extends AbstractAction {
		/**
		 * Constructor
		 */
		CloseFileAction() {
			putValue(Action.NAME, "Close");
			putValue(Action.SHORT_DESCRIPTION, "Close file");
		}

		/**
		 * Closes the tab with the file. If there are no more tabs open, close
		 * operation is not available.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (workspace.getTabCount() > 0) {
				workspace.remove(workspace.getSelectedIndex());
			}
			if (workspace.getTabCount() == 0) {
				closeFileAction.setEnabled(false);
			}
		}
	}

	/**
	 * @author Semen Martynov
	 * 
	 *         Displays a window with information about the program.
	 */
	private class AboutAction extends AbstractAction {
		/**
		 * Constructor
		 */
		AboutAction() {
			putValue(Action.NAME, "About");
			putValue(Action.SHORT_DESCRIPTION, "About this program");
		}

		/**
		 * Displays a window with information about the program.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"Text browser.\nSwing practice.", "About...",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * The working field, which displays the opened files.
	 */
	private JTabbedPane workspace = new JTabbedPane();
	/**
	 * The event file is closed (not available when there are no opened files).
	 */
	AbstractAction closeFileAction = new CloseFileAction();
}