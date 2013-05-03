/**
 * 
 */
package ru.spbau.martynov.task11;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

/**
 * @author sam
 * 
 */
public class AuthorizationDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AuthorizationDialog() {
		super("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(360, 140);
		setMinimumSize(new Dimension(360, 140));

		// Buttons
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login.setText("");
				password.setText("");
			}
		});
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (login.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Login is empty!",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "OK",
							"Registration compleeted",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "" + login.getHeight());
			}
		});

		// Login box
		Box loginBox = Box.createHorizontalBox();
		JLabel loginLabel = new JLabel("Login:");
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
		// passwordBox.setBorder(new LineBorder(Color.BLUE, 4));

		// Buttons box
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(clearButton);
		buttonBox.add(Box.createHorizontalStrut(6));
		buttonBox.add(registerButton);
		buttonBox.add(Box.createHorizontalStrut(6));
		buttonBox.add(loginButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.setMaximumSize(new Dimension(260, 24));

		// Size correction
		loginLabel.setPreferredSize(passwordLabel.getPreferredSize());

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
				"src/resources/logo.jpg").getImage());
		getContentPane().add(imagePanel);
	}

	class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image img;

		public ImagePanel(Image img) {
			this.img = img;
		}

		public void paintComponent(Graphics g) {
			int min = Math.min(getWidth(), getHeight());
			min = Math.max(min - 12, 1);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(img, 6, 6, min, min, null);
		}

	}

	private JProgressBar progressBar = new JProgressBar(
			JProgressBar.HORIZONTAL, 0, 100);
	private JTextField login = new JTextField();
	private JPasswordField password = new JPasswordField();

}
