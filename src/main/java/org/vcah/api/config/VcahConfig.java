package org.vcah.api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.vcah.importer.Loader;
import org.vcah.model.Deck;
import org.vcah.model.Game;
import org.vcah.repository.GameRepository;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(VcahProperties.class)
public class VcahConfig
{
	private final VcahProperties properties;
	private final GameRepository gameRepository;
	private final Loader<Deck> deckLoader;

	public VcahConfig(final GameRepository gameRepository, final Loader<Deck> deckLoader, final VcahProperties properties)
	{
		this.gameRepository = gameRepository;
		this.deckLoader = deckLoader;
		this.properties = properties;
	}

	@PostConstruct
	public void postConstruct()
	{
		final Game defaultGame = Game.builder()
				.name("_default_")
				.owner("otheriault")
				.whiteDeck(deckLoader.load(properties.getDeck().getWhite()))
				.blackDeck(deckLoader.load(properties.getDeck().getBlack()))
				.build();
		gameRepository.save(defaultGame);
	}
}
