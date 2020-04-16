package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player not found.")
public class PlayerNotFoundException extends RuntimeException
{
	public PlayerNotFoundException(String msg)
	{
		super(msg);
	}
}
