package org.vcah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vcah.model.Player;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String>
{
	List<Player> findDistinctPlayersByNameOrEmailAllIgnoreCase(String name, String email);
}
