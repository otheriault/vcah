package org.vcah.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vcah.model.Card;
import org.vcah.model.Deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
public class DeckLoader implements Loader<Deck>
{
	private static final Logger LOG = LoggerFactory.getLogger(DeckLoader.class);

	@Override
	public Deck load(final String filename)
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
						deck.getCards().add(output);
					}
					count++;
				}
			}
			LOG.info("Number of lines processed: " + count);

		}
		catch (IOException e)
		{
			throw new LoaderException("Unable to read file: " + filename, e);
		}
		catch (Exception e)
		{
			throw new LoaderException("An unknown exception occurred while trying to import file: " + filename, e);
		}
		return deck;
	}

}
