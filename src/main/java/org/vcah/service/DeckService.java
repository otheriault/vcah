package org.vcah.service;

import org.vcah.api.CardNotFoundException;
import org.vcah.api.DuplicateCardException;
import org.vcah.model.Card;
import org.vcah.model.Deck;

public interface DeckService
{
	boolean containsCard(Deck deck, Card card);

	void addCard(Deck deck, Card card) throws DuplicateCardException;

	void removeCard(Deck deck, Card card) throws CardNotFoundException;
}
