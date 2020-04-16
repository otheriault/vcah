package org.vcah.factory;

import org.springframework.stereotype.Component;
import org.vcah.model.Game;

@Component
public class GameFactory
{
	public static final String NAME_DEFAULT = "name-default";
	public static final String OWNER_DEFAULT = "owner-default";

	private final DeckFactory deckFactory;

	public GameFactory(final DeckFactory deckFactory)
	{
		this.deckFactory = deckFactory;
	}

	public Game newGameWithNoPlayers()
	{
		return Game.builder()
				.name(NAME_DEFAULT)
				.owner(OWNER_DEFAULT)
				.blackDeck(deckFactory.buildSimpleBlackDeck(10))
				.whiteDeck(deckFactory.buildSimpleWhiteDeck(100))
				.build();
	}

}
