package org.vcah.service;

import org.springframework.stereotype.Service;
import org.vcah.api.CardNotFoundException;
import org.vcah.api.DuplicateCardException;
import org.vcah.model.Card;
import org.vcah.model.Deck;
import org.vcah.repository.DeckRepository;

@Service
public class DeckServiceImpl implements DeckService
{
	private DeckRepository deckRepository;

	public DeckServiceImpl(DeckRepository deckRepository)
	{
		this.deckRepository = deckRepository;
	}

	@Override
	public boolean containsCard(Deck deck, Card card)
	{
		return deck.getCards().contains(card);
	}

	@Override
	public void addCard(final Deck deck, final Card card) throws DuplicateCardException
	{
		if (containsCard(deck, card))
		{
			throw new DuplicateCardException("Deck with id [" + deck.getId() + "] already contains a card with text [" + card.getText() + "].");
		}
		deck.getCards().add(card.getText());
		deckRepository.save(deck);
	}

	@Override
	public void removeCard(Deck deck, Card card) throws CardNotFoundException
	{
		boolean exists = deck.getCards().remove(card);
		if (!exists)
		{
			throw new CardNotFoundException("No card found with text [" + card.getText() + "].");
		}
		deckRepository.save(deck);
	}

}
