package org.vcah.importer;

/**
 * Thrown if an exception occurs during importing.
 */
@SuppressWarnings("serial")
public class ImporterException extends RuntimeException
{
	public ImporterException(String message)
	{
		super(message);
	}

	public ImporterException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
