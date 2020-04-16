package org.vcah.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.vcah.util.validation.ValidDeckType;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck
{
	@Id
	private String id;

	@NotNull
	private String name;
	private String description;

	@ValidDeckType
	private String type;
	private String owner;

	@Builder.Default
	private List<String> cards = new ArrayList<>();
}
