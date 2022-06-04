package br.com.intech.iseasypos.core.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfOrCnpjValidator implements ConstraintValidator<CpfOrCnpj, String>{
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Pattern padrao;
		Matcher matcher;
		if(value != null && value.length() == 14) {
			padrao = Pattern.compile("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})");
		}else if(value != null && value.length() == 18 ) {
			padrao = Pattern.compile("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})");
		}else {
			return false;
		}
		matcher = padrao.matcher(value);
		return matcher.matches();

	}

}
