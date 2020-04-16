package org.vcah.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.vcah.Application;
import org.vcah.model.Game;
import org.vcah.scenario.GameScenario;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class GameRepositoryIntegrationTest
{
	@Autowired
	private GameScenario gameScenario;
	@Autowired
	private GameRepository gameRepository;

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
	public void getGamesByOwner_shouldSucceed()
	{
		final List<Game> games = gameRepository.findByOwner(context.game.getOwner());
		assertThat(games.get(0), equalTo(context.game));
	}
}
