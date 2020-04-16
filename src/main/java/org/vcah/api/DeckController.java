package org.vcah.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.vcah.model.Card;
import org.vcah.model.Deck;
import org.vcah.repository.DeckRepository;
import org.vcah.service.DeckService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/decks")
public class DeckController
{
	private static final Logger LOG = LoggerFactory.getLogger(DeckController.class);

	private final DeckService deckService;
	private final DeckRepository deckRepository;

	public DeckController(final DeckService deckService, final DeckRepository deckRespository)
	{
		this.deckService = deckService;
		this.deckRepository = deckRespository;
	}

	//    @SuppressWarnings("unchecked")
	//    @RequestMapping(method = RequestMethod.GET)
	//    @ResponseBody
	//    @ResponseStatus(HttpStatus.OK)
	//    public List<Deck> getDecksByOwner(@RequestParam("owner") String owner) {
	//
	//	LOG.trace("Getting decks by owner: " + owner);
	//
	//	return ListUtils.union(Lists.newArrayList(deckRepository.findByOwner(owner)),
	//		Lists.newArrayList(deckRepository.findByOwner(null)));
	//    }

	@GetMapping
	public List<Deck> getAllDecks()
	{
		LOG.trace("Getting all decks.");
		return deckRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public Deck getDeck(@PathVariable final String id)
	{
		LOG.trace("Getting deck: " + id);
		final Deck deck = getDeckById(id);
		return deck;
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateDeckInfo(@PathVariable final String id, @RequestBody @Valid final Deck deck)
	{
		deck.setCards(null);
		LOG.trace("Updating deck info: " + id);
		deck.setId(id);

		final Deck existingDeck = getDeckById(id);
		existingDeck.setDescription(deck.getDescription());
		existingDeck.setName(deck.getName());
		existingDeck.setType(deck.getType());
		deckRepository.save(existingDeck);
	}

	@PostMapping
	public ResponseEntity<Void> createDeck(@RequestBody @Valid final Deck deck)
	{
		deck.setId(null);
		deck.setCards(null);
		LOG.trace("Creating deck: " + deck);

		final String deckId = deckRepository.save(deck).getId();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(deckId).toUri());
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}/cards")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addCardToDeck(@PathVariable final String id, @RequestBody @Valid final Card card)
	{
		LOG.trace("Adding card to deck [" + id + "]: " + card);
		final Deck deck = getDeckById(id);
		if (deckService.containsCard(deck, card))
		{
			throw new DuplicateCardException("Deck with id [" + id + "] already contains a card with text [" + card.getText() + "].");
		}
	}

	@DeleteMapping(path = "/{id}/cards")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCardFromDeck(@PathVariable final String id, @RequestBody @Valid final Card card)
	{
		LOG.trace("Removing card from deck [" + id + "]: " + card);
		final Deck deck = getDeckById(id);
		deckService.removeCard(deck, card);
	}

	private Deck getDeckById(String id)
	{
		return deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException("No deck found with id [" + id + "]."));
	}

}
