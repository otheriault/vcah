package org.vcah.factory;

import org.springframework.stereotype.Component;
import org.vcah.model.Game;
import org.vcah.model.GameState;

import java.util.Arrays;

@Component
public class GameFactory
{
	public static final String NAME_DEFAULT = "name-default";
	public static final String OWNER_DEFAULT = "owner-default";
	public static final String CARD_TEXT_BLACK_PREFIX = "black_";
	public static final String CARD_TEXT_WHITE_PREFIX = "white_";

	public Game newGameWithNoPlayers()
	{
		return Game.builder()
				.name(NAME_DEFAULT)
				.owner(OWNER_DEFAULT)
				.state(GameState.NEW.getValue())
				.blackDeck(Arrays.asList(buildCards(CARD_TEXT_BLACK_PREFIX, 10)))
				.whiteDeck(Arrays.asList(buildCards(CARD_TEXT_WHITE_PREFIX, 100)))
				.build();
	}

	protected String[] buildCards(String prefix, int numCards)
	{
		final String[] cards = new String[numCards];
		for (int i = 0; i < numCards; i++)
		{
			cards[i] = prefix + i;
		}
		return cards;
	}

}
