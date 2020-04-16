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
import org.vcah.model.Player;
import org.vcah.scenario.PlayerScenario;

import java.net.URI;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class PlayerControllerIntegrationTest
{
	private static final String API_V1_PLAYERS = "/api/v1/players/";
	private static final String INVALID = "invalid";

	@Autowired
	private PlayerScenario playerScenario;
	@Autowired
	private TestRestTemplate restTemplate;

	private PlayerScenario.Context context;

	@Before
	public void setUp()
	{
		context = playerScenario.createPlayers();
	}

	@After
	public void tearDown()
	{
		playerScenario.deleteAll();
	}

	@Test
	public void getPlayers_shouldReturnAllPlayers()
	{
		final ResponseEntity<Player[]> response = restTemplate.getForEntity(API_V1_PLAYERS, Player[].class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody()[0], equalTo(context.alice));
		assertThat(response.getBody()[1], equalTo(context.bob));
	}

	@Test
	public void getPlayers_shouldReturnEmptyCollection_NoPlayersFound()
	{
		playerScenario.deleteAll();
		final ResponseEntity<Player[]> response = restTemplate.getForEntity(API_V1_PLAYERS, Player[].class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), emptyArray());
	}

	@Test
	public void getPlayer_shouldReturnPlayer()
	{
		final ResponseEntity<Player> response = restTemplate.getForEntity(API_V1_PLAYERS + context.alice.getId(), Player.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), equalTo(context.alice));
	}

	@Test
	public void getPlayer_shouldReturnNotFound_PlayerDoesNotExist()
	{
		final ResponseEntity<Player> response = restTemplate.getForEntity(API_V1_PLAYERS + INVALID, Player.class);

		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void createPlayer_shouldReturnSuccess()
	{
		final Player player = Player.builder().name("new-player-name").email("new@vcah.org").build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_PLAYERS, player, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void createPlayer_shouldReturnLocation()
	{
		final Player player = Player.builder().name("new-player-name").email("new@vcah.org").build();

		final URI location = restTemplate.postForLocation(API_V1_PLAYERS, player);
		assertThat(location.getPath(), Matchers.containsString(API_V1_PLAYERS));
	}

	@Test
	public void createPlayer_shouldReturnFailure_MissingEmail()
	{
		final Player player = Player.builder().name("new-player-name").email(null).build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_PLAYERS, player, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void createPlayer_shouldReturnFailure_MissingName()
	{
		final Player player = Player.builder().name(null).email("new@vcah.org").build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_PLAYERS, player, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void createPlayer_shouldReturnFailure_InvalidEmail()
	{
		final Player player = Player.builder().name("new-player-name").email(INVALID).build();

		final ResponseEntity<Void> response = restTemplate.postForEntity(API_V1_PLAYERS, player, Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void updatePlayer_shouldReturnSuccess()
	{
		final HttpEntity<Player> player = new HttpEntity<>(Player.builder().name("new-player-name").email("new@vcah.org").build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_PLAYERS + context.alice.getId(), HttpMethod.PUT, player,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}

	@Test
	public void updatePlayer_shouldReturnFailure_MissingName()
	{
		final HttpEntity<Player> player = new HttpEntity(Player.builder().name(null).email("new@vcah.org").build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_PLAYERS + context.alice.getId(), HttpMethod.PUT, player,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void updatePlayer_shouldReturnFailure_MissingEmail()
	{
		final HttpEntity<Player> player = new HttpEntity<>(Player.builder().name("new-player-name").email(null).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_PLAYERS + context.alice.getId(), HttpMethod.PUT, player,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void updatePlayer_shouldReturnFailure_InvalidEmail()
	{
		final HttpEntity<Player> player = new HttpEntity<>(Player.builder().name("new-player-name").email(INVALID).build());

		final ResponseEntity<Void> response = restTemplate.exchange(API_V1_PLAYERS + context.alice.getId(), HttpMethod.PUT, player,
				Void.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

}