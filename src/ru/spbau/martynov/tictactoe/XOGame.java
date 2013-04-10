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

import java.util.ArrayList;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class for tic-tac-toe. Allows players to walk and determinate the
 *         winner. Allows to iterate on all statuses of a field.
 */
public class XOGame extends Game {

	/**
	 * Constructor. Receives starting values for new game creation. Players are
	 * transferred to parent class, for check game type.
	 * 
	 * @param fieldSize
	 *            - Size of any field's sides.
	 * @param numberInRow
	 *            - Victory conditions.
	 * @param player1
	 *            - The first player.
	 * @param player2
	 *            - The second player.
	 * @throws IllegalArgumentException
	 *             if fieldSize < numberInRow
	 * @throws RuntimeException
	 *             - if both players have the same token.
	 * @throws NegativeArraySizeException
	 *             - if fieldSize < 0
	 */
	public XOGame(int fieldSize, int numberInRow, Player player1, Player player2)
			throws IllegalArgumentException, RuntimeException,
			NegativeArraySizeException {
		super(player1, player2);
		this.player1 = player1;
		this.player2 = player2;
		if (this.player1.getToken() == this.player2.getToken()) {
			throw new RuntimeException("Both players are in the same team...");
		}
		if (fieldSize < numberInRow) {
			throw new IllegalArgumentException("Field is too small.");
		}
		this.fieldSize = fieldSize;
		positions = new ArrayList<Position>();
		positions.add(new Position(this.fieldSize));
		this.numberInRow = numberInRow;
	}

	/**
	 * Function allows players to walk, while the winner won't be defined.
	 * 
	 * @return Winner's token.
	 */
	@Override
	public int getWinner() {
		int i;
		while ((i = checkCurrentState()) == 0) {
			positions.add(player1.move(positions.get(positions.size() - 1)));
			if ((i = checkCurrentState()) != 0) {
				return i;
			}
			positions.add(player2.move(positions.get(positions.size() - 1)));
		}
		return i;
	}

	/**
	 * Function for determination of the winner
	 * 
	 * @return Winner's token.
	 */
	private int checkCurrentState() {
		int[][] localField = positions.get(positions.size() - 1).toArray();

		// Check for available points
		boolean freePoint = false;

		for (int x = 0; x != fieldSize; x++) {
			for (int y = 0; y != fieldSize; y++) {
				if (localField[x][y] != 0) {

					// Check for winner
					boolean match = false;

					match = checkHorizontalRow(localField, x, y);

					if (!match) {
						match = checkVerticalRow(localField, x, y);
					}

					if (!match) {
						match = checkDiagonalLeftRow(localField, x, y);
					}

					if (!match) {
						match = checkDiagonalRightRow(localField, x, y);
					}

					if (match) {
						// We have WINNER!
						return localField[x][y];
					}
				} else {
					// Free points still exist
					freePoint = true;
				}
			}
		}

		if (freePoint) {
			// Free points still exist - keep on playing
			return 0;
		} else {
			// Free points doen't exist - draw
			return -1;
		}
	}

	/**
	 * Function checks achievement the victory conditions on a horizontal
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return - true if conditions are satisfied, false otherwise.
	 */
	private boolean checkHorizontalRow(int[][] localField, int x, int y) {
		if (y + numberInRow > fieldSize) {
			return false;
		}

		for (int j = y; j < y + numberInRow; j++) {
			if (localField[x][y] != localField[x][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks achievement the victory conditions on a vertical
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return - true if conditions are satisfied, false otherwise.
	 */
	private boolean checkVerticalRow(int[][] localField, int x, int y) {
		if (x + numberInRow > fieldSize) {
			return false;
		}

		for (int i = x; i < x + numberInRow; i++) {
			if (localField[x][y] != localField[i][y]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks achievement the victory conditions on a diagonal (right
	 * to left)
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return - true if conditions are satisfied, false otherwise.
	 */
	private boolean checkDiagonalLeftRow(int[][] localField, int x, int y) {
		if (x + numberInRow > fieldSize || y - numberInRow < 0) {
			return false;
		}

		for (int i = x, j = y; i < x + numberInRow; i++, j--) {
			if (localField[x][y] != localField[i][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks achievement the victory conditions on a diagonal (left to
	 * right)
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return - true if conditions are satisfied, false otherwise.
	 */
	private boolean checkDiagonalRightRow(int[][] localField, int x, int y) {
		if (x + numberInRow > fieldSize || y + numberInRow > fieldSize) {
			return false;
		}

		for (int i = x, j = y; i < x + numberInRow; i++, j++) {
			if (localField[x][y] != localField[i][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Size of a game field
	 */
	private final int fieldSize;
	/**
	 * Quantity of tokens, for the victory achievement.
	 */
	private final int numberInRow;
	/**
	 * The first player.
	 */
	private final Player player1;
	/**
	 * The second player.
	 */
	private final Player player2;
}
