package org.vcah.factory;

import org.springframework.stereotype.Component;
import org.vcah.model.Deck;
import org.vcah.model.DeckType;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeckFactory
{
	public static final String NAME_DEFAULT = "name-default";
	public static final String DESCRIPTION_DEFAULT = "description-default";
	public static final String OWNER_DEFAULT = "owner-default";
	public static final String CARD_TEXT_PREFIX = "card_";
	public static final String CARD_TEXT_CARD_0 = CARD_TEXT_PREFIX + "0";

	public Deck buildSimpleBlackDeck(final int numCards)
	{
		final Deck deck = new Deck();
		deck.setName(NAME_DEFAULT);
		deck.setDescription(DESCRIPTION_DEFAULT);
		deck.setOwner(OWNER_DEFAULT);
		deck.setType(DeckType.BLACK.getValue());
		deck.setCards(buildCards(numCards));
		return deck;
	}

	public Deck buildSimpleWhiteDeck(final int numCards)
	{
		final Deck deck = new Deck();
		deck.setName(NAME_DEFAULT);
		deck.setDescription(DESCRIPTION_DEFAULT);
		deck.setOwner(OWNER_DEFAULT);
		deck.setType(DeckType.WHITE.getValue());
		deck.setCards(buildCards(numCards));
		return deck;
	}

	protected List<String> buildCards(final int numCards)
	{
		final List<String> cards = new ArrayList<>();
		for (int i = 0; i < numCards; i++)
		{
			cards.add(CARD_TEXT_PREFIX + i);
		}
		return cards;
	}

}
