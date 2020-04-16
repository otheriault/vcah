package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "There is already a player with this email address.")
public class DuplicatePlayerException extends RuntimeException
{
	public DuplicatePlayerException(String msg)
	{
		super(msg);
	}
}
