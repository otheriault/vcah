package org.vcah.factory;

import org.springframework.stereotype.Component;
import org.vcah.model.Player;

@Component
public class PlayerFactory
{
	public static final String NAME_ALICE = "Alie";
	public static final String NAME_BOB = "Bob";
	public static final String EMAIL_ALICE = "alice@vcah.org";
	public static final String EMAIL_BOB = "bob@vcah.org";

	public Player buildAlice()
	{
		return Player.builder().name(NAME_ALICE).email(EMAIL_ALICE).build();
	}

	public Player buildBob()
	{
		return Player.builder().name(NAME_BOB).email(EMAIL_BOB).build();
	}

}
