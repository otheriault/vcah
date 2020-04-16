package org.vcah.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "vcah")
public class VcahProperties
{
	private Deck deck;

	@Data
	public static class Deck
	{
		private String black;
		private String white;
	}
}
