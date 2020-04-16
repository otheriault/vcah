package org.vcah.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.vcah.model.Card;
import org.vcah.model.Deck;
import org.vcah.model.DeckType;

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

	protected List<Card> buildCards(final int numCards)
	{
		final List<Card> cards = new ArrayList<>();
		for (int i = 0; i < numCards; i++)
		{
			final Card card = new Card();
			card.setText(CARD_TEXT_PREFIX + i);
			cards.add(card);
		}
		return cards;
	}

}
