package org.vcah.scenario;

import org.springframework.stereotype.Component;
import org.vcah.factory.DeckFactory;
import org.vcah.model.Deck;
import org.vcah.repository.DeckRepository;

@Component
public class DeckScenario
{
	private final DeckFactory deckFactory;
	private final DeckRepository deckRepository;

	public DeckScenario(final DeckRepository deckRepository, final DeckFactory deckFactory)
	{
		this.deckRepository = deckRepository;
		this.deckFactory = deckFactory;
	}

	public Context createSimpleDecks(final int numBlackCards, final int numWhiteCards)
	{
		final Context context = Context.newInstance();
		context.blackDeck = deckRepository.save(deckFactory.buildSimpleBlackDeck(numBlackCards));
		context.whiteDeck = deckRepository.save(deckFactory.buildSimpleWhiteDeck(numWhiteCards));
		return context;
	}

	public void deleteAll()
	{
		deckRepository.deleteAll();
	}

	public static class Context
	{
		public Deck blackDeck;
		public Deck whiteDeck;

		public static Context newInstance()
		{
			return new Context();
		}
	}
}
