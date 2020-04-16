package org.vcah.api;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.vcah.Application;
import org.vcah.factory.DeckFactory;
import org.vcah.model.Card;
import org.vcah.model.Deck;
import org.vcah.model.DeckType;
import org.vcah.scenario.DeckScenario;

import java.net.URI;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class DeckControllerIntegrationTest
{
	private static final String CARDS = "/cards";
	private static final String API_V1_DECKS = "/api/v1/decks/";
	private static final String INVALID = "invalid";

	@Autowired
	private DeckScenario deckScenario;
	@Autowired
	private TestRestTemplate restTemplate;

	private DeckScenario.Context context;

	@Before
	public void setUp()
	{
		context = deckScenario.createSimpleDecks(5, 10);
	}

	@After
	public void tearDown()
	{
		deckScenario.deleteAll();
	}

	@Test
	public void getDecks_shouldReturnAllDecks()
	{
		final ResponseEntity<Deck[]> response = restTemplate.getForEntity(API_V1_DECKS, Deck[].class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody()[0], equalTo(context.blackDeck));
		assertThat(response.getBody()[1], equalTo(context.whiteDeck));
	}

	@Test
	public void getDecks_shouldReturnEmptyCollection_NoDecksFound()
	{
		deckScenario.deleteAll();
		final ResponseEntity<Deck[]> response = restTemplate.getForEntity(API_V1_DECKS, Deck[].class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), emptyArray());
	}

	@Test
	public void getDeck_shouldReturnDeck()
	{
		final ResponseEntity<Deck> response = restTemplate.getForEntity(API_V1_DECKS + context.blackDeck.getId(), Deck.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), equalTo(context.blackDeck));
	}

	@Test
	public void getDeck_shouldReturnNotFound_DeckDoesNotExist()
	{
		final ResponseEntity<Deck> response = restTemplate.getForEntity(API_V1_DECKS + INVALID, Deck.class);

		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void createDeck_shouldReturnSuccess()
	{
		final Deck deck = Deck.builder()
				.name("new-deck-name")
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(DeckType.BLACK.getValue())
				.build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_DECKS, deck, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void createDeck_shouldReturnLocation()
	{
		final Deck deck = Deck.builder()
				.name("new-deck-name")
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(DeckType.BLACK.getValue())
				.build();

		final URI location = restTemplate.postForLocation(API_V1_DECKS, deck);
		assertThat(location.getPath(), Matchers.containsString(API_V1_DECKS));
	}

	@Test
	public void createDeck_shouldReturnFailure_InvalidType()
	{
		final Deck deck = Deck.builder()
				.name("new-deck-name")
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(INVALID)
				.build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_DECKS, deck, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void createDeck_shouldReturnFailure_MissingName()
	{
		final Deck deck = Deck.builder()
				.name(null)
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(DeckType.BLACK.getValue())
				.build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_DECKS, deck, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void updateDeck_shouldReturnSuccess()
	{
		final HttpEntity<Deck> deck = new HttpEntity(Deck.builder()
				.name("new-deck-name")
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(DeckType.BLACK.getValue())
				.build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId(), HttpMethod.PUT, deck,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}

	@Test
	public void updateDeck_shouldReturnFailure_InvalidType()
	{
		final HttpEntity<Deck> deck = new HttpEntity<>(Deck.builder()
				.name("new-deck-name")
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(INVALID)
				.build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId(), HttpMethod.PUT, deck,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void updateDeck_shouldReturnFailure_MissingName()
	{
		final HttpEntity<Deck> deck = new HttpEntity<>(Deck.builder()
				.name(null)
				.description("new-deck-description")
				.owner("new-deck-owner")
				.type(DeckType.BLACK.getValue())
				.build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId(), HttpMethod.PUT, deck,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void addCardToDeck_shouldReturnSuccess()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text("new-card-text").build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS, HttpMethod.PUT,
				card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}

	@Test
	public void addCardToDeck_shouldFail_EmptyText()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text(null).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS, HttpMethod.PUT,
				card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void addCardToDeck_shouldFail_DuplicateCard()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text(DeckFactory.CARD_TEXT_CARD_0).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS, HttpMethod.PUT,
				card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
	}

	@Test
	public void removeCardFromDeck_shouldReturnSuccess()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text(DeckFactory.CARD_TEXT_CARD_0).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS,
				HttpMethod.DELETE, card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}

	@Test
	public void removeCardFromDeck_shouldFail_EmptyText()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text(null).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS,
				HttpMethod.DELETE, card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void removeCardFromDeck_shouldFail_CardNotFound()
	{
		final HttpEntity<Card> card = new HttpEntity<>(Card.builder().text(INVALID).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_DECKS + context.blackDeck.getId() + CARDS,
				HttpMethod.DELETE, card, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

}