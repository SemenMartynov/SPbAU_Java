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

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Semen Martynov
 * 
 *         Class of the abstract game. Allows to check passed classes on
 *         compliance to required game.
 * 
 */
public abstract class Game implements Iterable<Position> {

	/**
	 * Constructor. Receives two players.
	 * 
	 * @param player1
	 *            - The first player.
	 * @param player2
	 *            - The second player.
	 */
	public Game(Player player1, Player player2) {
		// Window -> Preferences -> Java -> Compiler -> Errors/Warnings ->
		// Deprecated and restricted API
		if (sun.reflect.Reflection.getCallerClass(2) != player1.getGameType()) {
			throw new RuntimeException(
					"The first player don't know how to play...");
		}
		if (sun.reflect.Reflection.getCallerClass(2) != player2.getGameType()) {
			throw new RuntimeException(
					"The second player don't know how to play...");
		}
	}

	/**
	 * Abstract method of determining the winner.
	 * 
	 * @return 1 if X wins, 2 if O wins, 0 if it is possible to continue to
	 *         play, -1 if the draw.
	 */
	public abstract int getWinner();

	/**
	 * Implementing an iterator to iterate field states.
	 * 
	 * @return iterator of field states.
	 */
	@Override
	public Iterator<Position> iterator() {
		Iterator<Position> iterator = positions.iterator();
		return iterator;
	}

	/**
	 * List of field states.
	 */
	protected List<Position> positions;
}
