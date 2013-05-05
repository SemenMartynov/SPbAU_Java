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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;

/**
 * @author Semen Martynov
 * 
 * Authorization dialog.
 */
@SuppressWarnings("serial")
public class AuthorizationDialog extends JFrame {

	/**
	 * Constructor
	 */
	AuthorizationDialog() {
		super("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(360, 145);
		setMinimumSize(new Dimension(360, 145));

		// Login box
		Box loginBox = Box.createHorizontalBox();
		JLabel loginLabel = new JLabel("Login:");
		loginLabel.setHorizontalAlignment(JLabel.RIGHT);
		loginBox.add(loginLabel);
		loginBox.add(Box.createHorizontalStrut(6));
		loginBox.add(login);
		loginBox.setMaximumSize(new Dimension(260, 24));
		// loginBox.setBorder(new LineBorder(Color.GREEN, 4));

		// Password box
		Box passwordBox = Box.createHorizontalBox();
		JLabel passwordLabel = new JLabel("Password:");
		passwordBox.add(passwordLabel);
		passwordBox.add(Box.createHorizontalStrut(6));
		passwordBox.add(password);
		passwordBox.setMaximumSize(new Dimension(260, 24));
		// Size correction
		loginLabel.setPreferredSize(passwordLabel.getPreferredSize());
		// passwordBox.setBorder(new LineBorder(Color.BLUE, 4));

		// Buttons box
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(new JButton(new ClearFormAction()) {
			{
				// setMaximumSize(new Dimension(100, 50));
			}
		});
		buttonBox.add(Box.createHorizontalStrut(6));
		buttonBox.add(new JButton(new RegisterAction()) {
			{
				// setMaximumSize(new Dimension(100, 50));
			}
		});
		buttonBox.add(Box.createHorizontalStrut(6));
		buttonBox.add(new JButton(new LoginAction()) {
			{
				// setMaximumSize(new Dimension(100, 50));
			}
		});
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.setMaximumSize(new Dimension(260, 24));

		// Left panel
		Box eastBox = Box.createVerticalBox();
		eastBox.setBorder(new EmptyBorder(6, 0, 6, 6));
		eastBox.add(loginBox);
		eastBox.add(Box.createVerticalStrut(6));
		eastBox.add(passwordBox);
		eastBox.add(Box.createVerticalGlue());
		eastBox.add(buttonBox);
		getContentPane().add(eastBox, BorderLayout.EAST);

		// Progress bar
		getContentPane().add(progressBar, BorderLayout.SOUTH);

		// Image
		JPanel imagePanel = new ImagePanel(new ImageIcon(
				"src/resources/logo.jpg").getImage(), 6);
		getContentPane().add(imagePanel);
	}

	/**
	 * @author Semen Martynov
	 *
	 * Class for inscribing images in a frame.
	 */
	class ImagePanel extends JPanel {
		/**
		 * The displayed image
		 */
		private final Image image;
		/**
		 * Offset from the edges
		 */
		private final int border;

		/**
		 * Constructor
		 * 
		 * @param img displayed image
		 * @param border offset from the edges (if the value is less than zero, then offset = 0).
		 */
		public ImagePanel(Image img, int border) {
			this.image = img;
			this.border = Math.max(0, border);
		}

		/**
	     * Calls the UI delegate's paint method, if the UI delegate
	     * is non-<code>null</code>.  We pass the delegate a copy of the
	     * <code>Graphics</code> object to protect the rest of the
	     * paint code from irrevocable changes
	     * (for example, <code>Graphics.translate</code>).
	     *
	     * @param g the <code>Graphics</code> object to protect
	     * @see #paint
	     * @see ComponentUI
	     */
		@Override
		protected void paintComponent(Graphics g) {
			int min = Math.min(getWidth(), getHeight());
			min = Math.max(min - border * 2, 1);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(image, border, border, min, min, null);
		}

	}

	/**
	 * @author Semen Martynov
	 *
	 *	Clears the username and password.
	 */
	private class ClearFormAction extends AbstractAction {
		/**
		 * Constructor
		 */
		ClearFormAction() {
			putValue(Action.NAME, "Clear");
			putValue(Action.SHORT_DESCRIPTION, "Clear form");
		}

		/**
		 * Clears the username and password.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			login.setText("");
			password.setText("");
		}
	}

	/**
	 * @author Semen Martynov
	 *
	 * Registers a new user in the system.
	 */
	private class RegisterAction extends AbstractAction {
		/**
		 * Constructor
		 */
		RegisterAction() {
			putValue(Action.NAME, "Register");
			putValue(Action.SHORT_DESCRIPTION, "Register user");
		}

		/**
		 * Registers a new user in the system.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (login.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Login is empty!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "OK",
						"Registration compleeted",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * @author Semen Martynov
	 *
	 * "Checks" the password and starts the browser.
	 */
	private class LoginAction extends AbstractAction {
		/**
		 * Constructor
		 */
		LoginAction() {
			putValue(Action.NAME, "Login");
			putValue(Action.SHORT_DESCRIPTION, "Login");
		}

		/**
		 * "Checks" the password and starts the browser.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			setEnabled(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			new Thread(new Runnable() {
				public void run() {
					for (int i = 0; i <= 100; i += 5) {
						try {
							progressBar.setValue(i);
							Thread.sleep(150);
						} catch (InterruptedException e1) {
							setEnabled(true);
							setCursor(Cursor
									.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							e1.printStackTrace(System.err);
						}
					}
					BrowserWindow notepad = new BrowserWindow(login.getText());
					setVisible(false);
					notepad.setVisible(true);
				}
			}).start();
		}
	}

	/**
	 * Progress bar
	 */
	private JProgressBar progressBar = new JProgressBar(
			JProgressBar.HORIZONTAL, 0, 100);
	/**
	 * The field for login.
	 */
	private JTextField login = new JTextField();
	
	/**
	 * The password field.
	 */
	private JPasswordField password = new JPasswordField();

}
