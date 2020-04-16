package org.vcah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vcah.model.Game;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String>
{
	List<Game> findByOwner(String owner);
}
