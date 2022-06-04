package br.com.intech.iseasypos.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { EmiteNFCEValidator.class })
public @interface EmiteNFCE {
	String message() default "Configuração do Documento Fiscal é Obrigatório!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String valueField();

	String configField();

	String contentField();
}
