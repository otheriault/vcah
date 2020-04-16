package org.vcah.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.vcah.model.Player;
import org.vcah.repository.PlayerRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/players")
public class PlayerController
{
	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

	private PlayerRepository playerRepository;

	public PlayerController(PlayerRepository deckRespository)
	{
		this.playerRepository = deckRespository;
	}

	//    @RequestMapping(method = RequestMethod.GET)
	//    @ResponseBody
	//    @ResponseStatus(HttpStatus.OK)
	//    public List<Player> getPlayersBySearchString(@RequestParam("search") String search) {
	//
	//	LOG.trace("Getting players by search string: " + search);
	//
	//	return Lists.newArrayList(playerRepository.findDistinctPlayersByNameOrEmailAllIgnoreCase(search, search));
	//    }

	@GetMapping
	public List<Player> getAllPlayers()
	{
		LOG.trace("Getting all players.");
		return playerRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public Player getPlayer(@PathVariable final String id)
	{
		LOG.trace("Getting player by id: " + id);
		final Player player = getPlayerById(id);
		return player;
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePlayerInfo(@PathVariable final String id, @RequestBody @Valid final Player player)
	{
		LOG.trace("Updating deck info: " + id);
		player.setId(id);

		final Player existingPlayer = getPlayerById(id);

		existingPlayer.setName(player.getName());
		playerRepository.save(existingPlayer);
	}

	@PostMapping
	public ResponseEntity<Void> createDeck(@RequestBody @Valid final Player player)
	{
		player.setId(null);
		LOG.trace("Creating player: " + player);

		final String playerId = playerRepository.save(player).getId();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(playerId).toUri());
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}

	private Player getPlayerById(String id)
	{
		return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("No player found with id [" + id + "]."));
	}

}
