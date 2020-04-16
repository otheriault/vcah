package org.vcah.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vcah.model.Deck;

import java.util.List;

public interface DeckRepository extends MongoRepository<Deck, String>
{
	List<Deck> findByOwner(String owner);
}
