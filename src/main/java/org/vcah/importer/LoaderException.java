package org.vcah.importer;

/**
 * Thrown if an exception occurs during importing.
 */
@SuppressWarnings("serial")
public class LoaderException extends RuntimeException
{
	public LoaderException(String message)
	{
		super(message);
	}

	public LoaderException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
