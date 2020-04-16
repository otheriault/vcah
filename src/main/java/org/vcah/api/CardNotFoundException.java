package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Card not found.")
public class CardNotFoundException extends RuntimeException
{
	public CardNotFoundException(String msg)
	{
		super(msg);
	}
}
