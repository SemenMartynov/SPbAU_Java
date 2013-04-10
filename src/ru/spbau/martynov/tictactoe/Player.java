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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class of the abstract player.
 * 
 */
public abstract class Player {

	/**
	 * Checking the type of game.
	 * 
	 * @return game class.
	 */
	public abstract Class<?> getGameType();

	/**
	 * Return player's name.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Player makes a move, changing board state.
	 * 
	 * @param field
	 *            - Current status of a game field.
	 * @return - status of a game field after move made.
	 */
	public abstract Position move(Position field);

	/**
	 * Get players's token
	 * 
	 * @return 1 if player's token is X, and 2 if O.
	 */
	public int getToken() {
		return token;
	}

	/**
	 * Function allows to reveal dangerous sections (item collection which can
	 * develop to the line, necessary for a victory).
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @return - true, if dangerous sections exist.
	 */
	protected boolean findCoincidence(int[][] localField) {
		int coincidenceLength = numberInRow;
		while (--coincidenceLength > 0 && me.isEmpty() && enemy.isEmpty()) {
			me.clear();
			enemy.clear();
			for (int x = 0; x != fieldSize; x++) {
				for (int y = 0; y != fieldSize; y++) {
					if (localField[x][y] != 0) {

						// Check horizontal row
						if (checkHorizontalRow(localField, x, y,
								coincidenceLength)) {
							// perspectivity check
							int preLenght = goLeft(localField, x, y);
							int postLenght = goRight(localField, x, y
									+ coincidenceLength);

							if (preLenght + coincidenceLength + postLenght >= fieldSize) {
								if (localField[x][y] == token) {
									me.add(new CoincidenceRow(x, y, x, y
											+ coincidenceLength));
								} else {
									enemy.add(new CoincidenceRow(x, y, x, y
											+ coincidenceLength));
								}
							}
						}

						// Check vertical row
						if (checkVerticalRow(localField, x, y,
								coincidenceLength)) {
							// perspectivity check
							int preLenght = goUp(localField, x, y);
							int postLenght = goDown(localField, x
									+ coincidenceLength, y);

							if (preLenght + coincidenceLength + postLenght >= fieldSize) {
								if (localField[x][y] == token) {
									me.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y));
								} else {
									enemy.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y));
								}
							}
						}

						// Check diagonal left row
						if (checkDiagonalLeftRow(localField, x, y,
								coincidenceLength)) {
							// perspectivity check
							int preLenght = goUpRight(localField, x, y);
							int postLenght = goDownLeft(localField, x
									+ coincidenceLength, y - coincidenceLength);

							if (preLenght + coincidenceLength + postLenght >= fieldSize) {
								if (localField[x][y] == token) {
									me.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y
											- coincidenceLength));
								} else {
									enemy.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y
											- coincidenceLength));
								}
							}
						}

						// Check diagonal right row
						if (checkDiagonalRightRow(localField, x, y,
								coincidenceLength)) {
							// perspectivity check
							int preLenght = goUpLeft(localField, x, y);
							int postLenght = goDownRight(localField, x
									+ coincidenceLength, y + coincidenceLength);

							if (preLenght + coincidenceLength + postLenght >= fieldSize) {
								if (localField[x][y] == token) {
									me.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y
											+ coincidenceLength));
								} else {
									enemy.add(new CoincidenceRow(x, y, x
											+ coincidenceLength, y
											+ coincidenceLength));
								}
							}
						}

					}
				}
			}
		}
		return (!me.isEmpty() || !enemy.isEmpty());
	}

	/**
	 * Function defines, in what side from given point it is worth building the
	 * line, and tries to put new point.
	 * 
	 * @param i
	 *            - Row number with which check begins.
	 * @param j
	 *            - Column number with which check begins.
	 * @param field
	 *            - game field
	 * @return new status of a game field.
	 */
	protected Position onePointMove(int i, int j, Position field) {
		Random random = new Random();
		int[][] localField = field.toArray();

		// horizontal perspectivity check
		int preLenght = goLeft(localField, i, j);
		int postLenght = goRight(localField, i, j);
		if (preLenght + 1 + postLenght >= numberInRow) {
			if (random.nextInt(2) == 0) {
				if (j - 1 >= 0 && localField[i][j - 1] == 0) {
					return field.occupyPoint(i, j - 1, token);
				}
				if (j + 1 < fieldSize && localField[i][j + 1] == 0) {
					return field.occupyPoint(i, j + 1, token);
				}
			} else {
				if (j + 1 < fieldSize && localField[i][j + 1] == 0) {
					return field.occupyPoint(i, j + 1, token);
				}
				if (j - 1 >= 0 && localField[i][j - 1] == 0) {
					return field.occupyPoint(i, j - 1, token);
				}
			}
		}

		// vertical perspectivity check
		preLenght = goUp(localField, i, j);
		postLenght = goDown(localField, i, j);
		if (preLenght + 1 + postLenght >= numberInRow) {
			if (random.nextInt(2) == 0) {
				if (i - 1 >= 0 && localField[i - 1][j] == 0) {
					return field.occupyPoint(i - 1, j, token);
				}
				if (i + 1 < fieldSize && localField[i + 1][j] == 0) {
					return field.occupyPoint(i + 1, j, token);
				}
			} else {
				if (i + 1 < fieldSize && localField[i + 1][j] == 0) {
					return field.occupyPoint(i + 1, j, token);
				}
				if (i - 1 >= 0 && localField[i - 1][j] == 0) {
					return field.occupyPoint(i - 1, j, token);
				}
			}
		}

		// diagonal left perspectivity check
		preLenght = goUpRight(localField, i, j);
		postLenght = goDownLeft(localField, i, j);
		if (preLenght + 1 + postLenght >= numberInRow) {
			if (random.nextInt(2) == 0) {
				if (i - 1 >= 0 && j + 1 < fieldSize
						&& localField[i - 1][j + 1] == 0) {
					return field.occupyPoint(i - 1, j + 1, token);
				}
				if (i + 1 < fieldSize && j - 1 >= 0
						&& localField[i + 1][j - 1] == 0) {
					return field.occupyPoint(i + 1, j - 1, token);
				}
			} else {
				if (i + 1 < fieldSize && j - 1 >= 0
						&& localField[i + 1][j - 1] == 0) {
					return field.occupyPoint(i + 1, j - 1, token);
				}
				if (i - 1 >= 0 && j + 1 < fieldSize
						&& localField[i - 1][j + 1] == 0) {
					return field.occupyPoint(i - 1, j + 1, token);
				}
			}
		}

		// diagonal right perspectivity check
		preLenght = goUpLeft(localField, i, j);
		postLenght = goDownRight(localField, i, j);
		if (preLenght + 1 + postLenght >= numberInRow) {
			if (random.nextInt(2) == 0) {
				if (i - 1 >= 0 && j - 1 >= 0 && localField[i - 1][j - 1] == 0) {
					return field.occupyPoint(i - 1, j - 1, token);
				}
				if (i + 1 < fieldSize && j + 1 < fieldSize
						&& localField[i + 1][j + 1] == 0) {
					return field.occupyPoint(i + 1, j + 1, token);
				}
			} else {
				if (i + 1 < fieldSize && j + 1 < fieldSize
						&& localField[i + 1][j + 1] == 0) {
					return field.occupyPoint(i + 1, j + 1, token);
				}
				if (i - 1 >= 0 && j - 1 >= 0 && localField[i - 1][j - 1] == 0) {
					return field.occupyPoint(i - 1, j - 1, token);
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @author Semen Martynov
	 * 
	 *         Class of dangerous sections (item collection which can develop to
	 *         the line, necessary for a victory).
	 * 
	 */
	protected class CoincidenceRow {

		/**
		 * Constructor. Takes the coordinates of dangerous section.
		 * 
		 * @param x1
		 *            - Top line number.
		 * @param y1
		 *            - Number of the left/right column (depends on the
		 *            direction).
		 * @param x2
		 *            - Bottom line number.
		 * @param y2
		 *            - Number of the right/left column (depends on the
		 *            direction).
		 */
		public CoincidenceRow(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		/**
		 * 
		 * 
		 * @return row number, before a dangerous section.
		 */
		public int getPreX() {
			if (x1 == x2) {
				return x1;
			} else {
				return x1 - 1;
			}
		}

		/**
		 * 
		 * @return Column number, before a dangerous section.
		 */
		public int getPreY() {
			if (y1 == y2) {
				return y1;
			} else if (y1 > y2) {
				return y1 + 1;
			} else {
				return y1 - 1;
			}
		}

		/**
		 * 
		 * @return Row number, after a dangerous section.
		 */
		public int getPostX() {
			if (x1 == x2) {
				return x2;
			} else {
				return x2 + 1;
			}
		}

		/**
		 * 
		 * @return Column number, after a dangerous section.
		 */
		public int getPostY() {
			if (y1 == y2) {
				return y2;
			} else if (y1 > y2) {
				return y2 - 1;
			} else {
				return y2 + 1;
			}
		}

		/**
		 * Top line number.
		 */
		private final int x1;
		/**
		 * Number of the left/right column (depends on the direction).
		 */
		private final int y1;
		/**
		 * Bottom line number.
		 */
		private final int x2;
		/**
		 * Number of the right/left column (depends on the direction).
		 */
		private final int y2;
	}

	/**
	 * Function checks, how many steps it is possible make the left direction,
	 * before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goLeft(int[][] localField, int x, int y) {
		int steps = 0;
		int j = y - 1;
		while (j >= 0
				&& (localField[x][j] == localField[x][y] || localField[x][j] == 0)) {
			steps++;
			j--;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the right direction,
	 * before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goRight(int[][] localField, int x, int y) {
		int steps = 0;
		int j = y + 1;
		while (j < fieldSize
				&& (localField[x][j] == localField[x][y] || localField[x][j] == 0)) {
			steps++;
			j++;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the upward direction,
	 * before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goUp(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x - 1;
		while (i >= 0
				&& (localField[i][y] == localField[x][y] || localField[i][y] == 0)) {
			steps++;
			i--;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the downward
	 * direction, before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goDown(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x + 1;
		while (i < fieldSize
				&& (localField[i][y] == localField[i][y] || localField[i][y] == 0)) {
			steps++;
			i++;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the upward left
	 * direction, before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goUpLeft(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x - 1;
		int j = y - 1;
		while (i >= 0
				&& j >= 0
				&& (localField[i][j] == localField[x][y] || localField[i][j] == 0)) {
			steps++;
			i--;
			j--;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the upward right
	 * direction, before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goUpRight(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x - 1;
		int j = y + 1;
		while (i >= 0
				&& j < fieldSize
				&& (localField[i][j] == localField[x][y] || localField[i][j] == 0)) {
			steps++;
			i--;
			j++;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the downward left
	 * direction, before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goDownLeft(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x + 1;
		int j = y - 1;
		while (i < fieldSize
				&& j >= 0
				&& (localField[i][j] == localField[x][y] || localField[i][j] == 0)) {
			steps++;
			i++;
			j--;
		}
		return steps;
	}

	/**
	 * Function checks, how many steps it is possible make the downward right
	 * direction, before meet card boundary or other player's token.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @return number of steps.
	 */
	protected int goDownRight(int[][] localField, int x, int y) {
		int steps = 0;
		int i = x + 1;
		int j = y + 1;
		while (i < fieldSize
				&& j < fieldSize
				&& (localField[i][j] == localField[x][y] || localField[i][j] == 0)) {
			steps++;
			i++;
			j++;
		}
		return steps;
	}

	/**
	 * Function checks if quantity of tokens on a horizontal row is not less
	 * than the required number.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @param coincidenceLength
	 *            - Necessary number of coincidence.
	 * @return true if there is required number of matches, or false otherwise.
	 */
	protected boolean checkHorizontalRow(int[][] localField, int x, int y,
			int coincidenceLength) {
		if (y + coincidenceLength > fieldSize) {
			return false;
		}

		for (int j = y; j < y + coincidenceLength; j++) {
			if (localField[x][y] != localField[x][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks if quantity of tokens on a vertical row is not less than
	 * the required number.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @param coincidenceLength
	 *            - Necessary number of coincidence.
	 * @return true if there is required number of matches, or false otherwise.
	 */
	protected boolean checkVerticalRow(int[][] localField, int x, int y,
			int coincidenceLength) {
		if (x + coincidenceLength > fieldSize) {
			return false;
		}

		for (int i = x; i < x + coincidenceLength; i++) {
			if (localField[x][y] != localField[i][y]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks if quantity of tokens on a diagonal left row is not less
	 * than the required number.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @param coincidenceLength
	 *            - Necessary number of coincidence.
	 * @return true if there is required number of matches, or false otherwise.
	 */
	protected boolean checkDiagonalLeftRow(int[][] localField, int x, int y,
			int coincidenceLength) {
		if (x + coincidenceLength > fieldSize || y - numberInRow < 0) {
			return false;
		}

		for (int i = x, j = y; i < x + coincidenceLength; i++, j--) {
			if (localField[x][y] != localField[i][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Function checks if quantity of tokens on a diagonal right row is not less
	 * than the required number.
	 * 
	 * @param localField
	 *            - Current status of a game field.
	 * @param x
	 *            - Row number with which check begins.
	 * @param y
	 *            - Column number with which check begins.
	 * @param coincidenceLength
	 *            - Necessary number of coincidence.
	 * @return true if there is required number of matches, or false otherwise.
	 */
	protected boolean checkDiagonalRightRow(int[][] localField, int x, int y,
			int coincidenceLength) {
		if (x + coincidenceLength > fieldSize || y + numberInRow > fieldSize) {
			return false;
		}

		for (int i = x, j = y; i < x + coincidenceLength; i++, j++) {
			if (localField[x][y] != localField[i][j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * List of mine dangerous zones.
	 */
	protected List<CoincidenceRow> me = new LinkedList<>();
	/**
	 * List of enemy's dangerous zones
	 */
	protected List<CoincidenceRow> enemy = new LinkedList<>();

	/**
	 * Size of a game field
	 */
	protected int fieldSize;
	/**
	 * Quantity of tokens, for the victory achievement.
	 */
	protected int numberInRow;

	/**
	 * Current player's token: 1 if X, 2 if O.
	 */
	protected int token;
}
