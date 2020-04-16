package org.vcah.importer;

public interface Loader<T>
{
	T load(String filename) throws LoaderException;
}
