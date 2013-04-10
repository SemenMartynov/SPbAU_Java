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

import java.util.Random;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class of artificial intelligence "Naive".
 * 
 */
public class XOPlayerNaive extends Player {

	/**
	 * Game class which corresponds the current player.
	 */
	public final static Class<?> GAME = XOGame.class;

	/**
	 * Constructor.
	 * 
	 * @param fieldSize
	 *            - Size of a game field.
	 * @param numberInRow
	 *            - Victory conditions.
	 * @param isX
	 *            - Player's token: true if X, false if O.
	 */
	public XOPlayerNaive(int fieldSize, int numberInRow, boolean isX) {
		if (fieldSize < numberInRow) {
			throw new IllegalArgumentException("Field is too small.");
		}
		this.fieldSize = fieldSize;
		this.numberInRow = numberInRow;
		if (isX) {
			token = 1;
		} else {
			token = 2;
		}
	}

	/**
	 * Get game class.
	 * 
	 * @return Game class which corresponds the current player.
	 */
	@Override
	public Class<?> getGameType() {
		return GAME;
	}

	/**
	 * Get player's name.
	 * 
	 * @return player's name.
	 */
	@Override
	public String getName() {
		return "Naive";
	}

	/**
	 * Implementation of algorithm of new point choosing.
	 * 
	 * @param field
	 *            - Current status of a game field.
	 * @return - status of a game field after move made.
	 */
	@Override
	public Position move(Position field) {
		Random random = new Random();
		int[][] localField = field.toArray();

		// Let's try to find lonely point
		for (int i = 0; i != fieldSize; i++) {
			for (int j = 0; j != fieldSize; j++) {
				if (localField[i][j] == token) {
					onePointMove(i, j, field);
				}
			}

		}
		// The worst case...
		// Check for available points
		for (int i = 0; i != fieldSize; i++) {
			for (int j = 0; j != fieldSize; j++) {
				if (localField[i][j] == 0) {
					// At least, one free point exist! We can use hysterical
					// random.
					do {
						i = random.nextInt(fieldSize);
						j = random.nextInt(fieldSize);
					} while (localField[i][j] != 0);
					return field.occupyPoint(i, j, token);
				}
			}

		}
		// It's impossible:
		throw new RuntimeException(this.getName() + " can't move");
	}
}
