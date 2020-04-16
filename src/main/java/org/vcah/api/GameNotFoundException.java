package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game not found.")
public class GameNotFoundException extends RuntimeException
{
	public GameNotFoundException(String msg)
	{
		super(msg);
	}
}
