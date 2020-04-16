package org.vcah.importer;

public interface Importer<T>
{
	T run(String filename) throws ImporterException;
}
