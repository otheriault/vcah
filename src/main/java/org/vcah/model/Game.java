package org.vcah.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Game
{
	@Id
	private String id;
	private String name;
	private String owner;
	private List<String> players;
	private Deck blackDeck;
	private Deck whiteDeck;

	private String czar;
	private String blackCard;
}
