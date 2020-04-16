package org.vcah.scenario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vcah.factory.GameFactory;
import org.vcah.model.Game;
import org.vcah.repository.GameRepository;

@Component
public class GameScenario
{
	private GameFactory gameFactory;
	private GameRepository gameRepository;

	@Autowired
	public GameScenario(GameRepository gameRepository, GameFactory gameFactory)
	{
		this.gameRepository = gameRepository;
		this.gameFactory = gameFactory;
	}

	public Context createNewGame()
	{
		final Context context = Context.newInstance();
		context.game = gameRepository.save(gameFactory.newGameWithNoPlayers());
		return context;
	}

	public void deleteAll()
	{
		gameRepository.deleteAll();
	}

	public static class Context
	{
		public Game game;

		public static Context newInstance()
		{
			return new Context();
		}
	}
}
