package org.vcah.service;

import org.vcah.model.Game;

public interface GameService
{
	void addPlayerToGame(final Game game, final String player);

	void removePlayerFromGame(final Game game, final String player);

	void prepareNextRound(final Game game);
}
