package org.vcah.scenario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vcah.factory.PlayerFactory;
import org.vcah.model.Player;
import org.vcah.repository.PlayerRepository;

@Component
public class PlayerScenario
{
	private PlayerFactory playerFactory;
	private PlayerRepository playerRepository;

	@Autowired
	public PlayerScenario(PlayerRepository playerRepository, PlayerFactory playerFactory)
	{
		this.playerRepository = playerRepository;
		this.playerFactory = playerFactory;
	}

	public Context createPlayers()
	{
		final Context context = Context.newInstance();
		context.alice = playerRepository.save(playerFactory.buildAlice());
		context.bob = playerRepository.save(playerFactory.buildBob());
		return context;
	}

	public void deleteAll()
	{
		playerRepository.deleteAll();
	}

	public static class Context
	{
		public Player alice;
		public Player bob;

		public static Context newInstance()
		{
			return new Context();
		}
	}
}
