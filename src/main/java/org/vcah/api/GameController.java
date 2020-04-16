package org.vcah.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vcah.model.Game;
import org.vcah.repository.GameRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/games")
public class GameController
{
	private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

	private final GameRepository gameRepository;

	public GameController(final GameRepository deckRespository)
	{
		this.gameRepository = deckRespository;
	}

	//    @RequestMapping(method = RequestMethod.GET)
	//    @ResponseStatus(HttpStatus.OK)
	//    public List<Game> getAllGames() {
	//
	//	LOG.trace("Getting all games.");
	//	return gameRepository.findAll();
	//    }

	@GetMapping
	public List<Game> getGamesByOwner(@RequestParam final String owner)
	{
		LOG.trace("Getting all games for owner [" + owner + "].");
		return gameRepository.findByOwner(owner);
	}

	@GetMapping(path = "/{id}")
	public Game getGame(@PathVariable final String id)
	{
		LOG.trace("Getting game by id: " + id);
		return getGameById(id);
	}

	private Game getGameById(String id)
	{
		return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("No game found with id [" + id + "]."));
	}

}
