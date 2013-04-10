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

/**
 * 
 * @author Semen Martynov
 * 
 *         Class for saving the state of a game field on some moment.
 * 
 */
public class Position {

	/**
	 * Constructor creates a square field on length of one of the sides.
	 * 
	 * @param fieldSize
	 *            - length of one of the sides.
	 */
	public Position(int fieldSize) {
		if (fieldSize < 0) {
			throw new NegativeArraySizeException(
					"Сan't create an array with negative length.");
		}
		field = new int[fieldSize][];
		for (int i = 0; i < fieldSize; i++) {
			field[i] = new int[fieldSize];
		}
	}

	/**
	 * Constructor creates a square field on a two-dimensional array.
	 * 
	 * @param tempPosition
	 *            - two-dimensional array.
	 */
	private Position(int[][] tempPosition) {
		field = tempPosition;
	}

	/**
	 * Function allows player to occupy a point, and changes a field status.
	 * 
	 * @param x
	 *            - Field row number.
	 * @param y
	 *            - Field column number.
	 * @param val
	 *            - Token of the player.
	 * @return - New field, with occupied point.
	 */
	public Position occupyPoint(int x, int y, int val) {
		if (x < 0 || y < 0 || x >= field.length || y >= field.length) {
			throw new ArrayIndexOutOfBoundsException(
					"Can't find requested point on the field.");
		}
		if (val < 1 || val > 2) {
			throw new IllegalArgumentException("Unknown occupier type.");
		}
		if (field[x][y] != 0) {
			throw new IllegalArgumentException(
					"Required point is already occupied.");
		}

		int[][] tempField = field;
		tempField[x][y] = val;
		return new Position(tempField);
	}

	/**
	 * Function turns a field into a two-dimensional array.
	 * 
	 * @return - two-dimensional array.
	 */
	public int[][] toArray() {
		return field;
	}

	/**
	 * Function for display a game field status.
	 * 
	 * @return - Line with a game field.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i != field.length; i++) {
			for (int j = 0; j != field.length; j++) {
				result.append(" ");
				result.append(field[i][j]);

			}
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * The two-dimensional array stores a status of a game field.
	 */
	private final int[][] field;
}
