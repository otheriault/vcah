package org.vcah.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vcah.model.Card;
import org.vcah.model.Deck;
import org.vcah.repository.DeckRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Component
public class DeckImporter implements Importer<Deck>
{
	private static final Logger LOG = LoggerFactory.getLogger(DeckImporter.class);

	private final DeckRepository deckRepository;
	private final String[] filenames;

	public DeckImporter(final DeckRepository deckRepository, @Value("${vcah.deck.default.filenames}") final String[] filenames)
	{
		this.deckRepository = deckRepository;
		this.filenames = filenames;
	}

	@Override
	public Deck run(final String filename)
	{
		final Deck deck = new Deck();
		deck.setCards(new ArrayList<>());

		try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename))))
		{
			boolean nameProcessed = false;
			String output;
			int count = 0;

			while ((output = br.readLine()) != null)
			{
				// Ignore lines that start with #
				if (!output.startsWith("#"))
				{
					if (!nameProcessed)
					{
						LOG.debug("Processing deck name: {}", output);
						deck.setName(output);
						deck.setType(output);
						deck.setDescription("This is a default deck to get you started.");
						nameProcessed = true;
					}
					else
					{
						LOG.debug("Processing card: {}", output);
						final Card card = new Card();
						card.setText(output);
						deck.getCards().add(card);
					}
					count++;
				}
			}
			LOG.info("Number of lines processed: " + count);

		}
		catch (IOException e)
		{
			throw new ImporterException("Unable to read file: " + filename, e);
		}
		catch (Exception e)
		{
			throw new ImporterException("An unknown exception occurred while trying to import file: " + filename, e);
		}
		return deck;
	}

	@PostConstruct
	public void postConstruct()
	{
		if (Objects.nonNull(filenames))
		{
			Arrays.asList(filenames).stream().map(filename -> run(filename)).forEach(deck -> deckRepository.save(deck));
		}
	}

}
