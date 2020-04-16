package org.vcah.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.vcah.Application;
import org.vcah.model.Game;
import org.vcah.scenario.GameScenario;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * REST web test for game queries.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class GameControllerIntegrationTest
{
	private static final String API_V1_GAMES_BY_OWNER = "/api/v1/games?owner={owner}";
	private static final String API_V1_GAMES = "/api/v1/games/";
	private static final String INVALID = "invalid";

	@Autowired
	private GameScenario gameScenario;

	@Autowired
	private TestRestTemplate restTemplate;

	private GameScenario.Context context;

	@Before
	public void setUp()
	{
		context = gameScenario.createNewGame();
	}

	@After
	public void tearDown()
	{
		gameScenario.deleteAll();
	}

	@Test
	public void getGamesByOwner_shouldReturnAllGames()
	{
		final ResponseEntity<Game[]> response = restTemplate.getForEntity(API_V1_GAMES_BY_OWNER, Game[].class,
				context.game.getOwner());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody()[0], equalTo(context.game));
	}

	@Test
	public void getGames_shouldReturnEmptyCollection_NoGamesFound()
	{
		gameScenario.deleteAll();
		final ResponseEntity<Game[]> response = restTemplate.getForEntity(API_V1_GAMES_BY_OWNER, Game[].class, INVALID);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), emptyArray());
	}

	@Test
	public void getGame_shouldReturnGame()
	{
		final ResponseEntity<Game> response = restTemplate.getForEntity(API_V1_GAMES + context.game.getId(), Game.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), equalTo(context.game));
	}

	@Test
	public void getGame_shouldReturnNotFound_GameDoesNotExist()
	{
		final ResponseEntity<Game> response = restTemplate.getForEntity(API_V1_GAMES + INVALID, Game.class);

		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
}
