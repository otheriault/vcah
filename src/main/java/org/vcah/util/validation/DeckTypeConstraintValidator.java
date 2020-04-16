package org.vcah.util.validation;

import org.springframework.stereotype.Component;
import org.vcah.model.DeckType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class DeckTypeConstraintValidator implements ConstraintValidator<ValidDeckType, String>
{
	@Override
	public void initialize(final ValidDeckType constraintAnnotation)
	{
		// Do Nothing
	}

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context)
	{
		return Arrays.asList(DeckType.values()).stream().anyMatch(deckType -> deckType.getValue().equals(value));
	}

}
