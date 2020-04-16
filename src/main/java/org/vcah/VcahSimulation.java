package org.vcah.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vcah.model.Deck;
import org.vcah.model.Player;

public class VcahSimulation
{
	private static final Logger LOG = LoggerFactory.getLogger(VcahSimulation.class);

	private Deck blackDeck;
	private Deck whiteDeck;
	private Player[] players;

	public VcahSimulation withDefaultDecks()
	{
		return this;
	}

	public VcahSimulation withNumPlayers(int numPlayers)
	{
		return this;
	}

	public void run()
	{
		// Execute simulation
	}
}
