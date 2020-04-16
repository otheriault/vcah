package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Deck not found.")
public class DeckNotFoundException extends RuntimeException
{
	public DeckNotFoundException(String msg)
	{
		super(msg);
	}
}
