package org.vcah.model;

public enum GameState
{
	NEW("new"), STARTED("started"), ENDED("ended");

	private String value;

	GameState(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
