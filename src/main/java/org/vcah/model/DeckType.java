package org.vcah.model;

public enum DeckType
{
	BLACK("black"), WHITE("white");

	private String value;

	DeckType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
