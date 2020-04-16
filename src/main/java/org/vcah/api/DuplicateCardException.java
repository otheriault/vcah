package org.vcah.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Deck already contains this card.")
public class DuplicateCardException extends RuntimeException
{
	public DuplicateCardException(String msg)
	{
		super(msg);
	}
}
