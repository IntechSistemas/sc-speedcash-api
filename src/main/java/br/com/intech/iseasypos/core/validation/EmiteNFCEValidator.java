package br.com.intech.iseasypos.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

import br.com.intech.iseasypos.domain.model.ConfiguracaoDocumentoFiscal;

public class EmiteNFCEValidator implements ConstraintValidator<EmiteNFCE, Object>{
	private String valueField;
	private String configField;
	
	@Override
	public void initialize(EmiteNFCE constraintAnnotation) {
		this.valueField = constraintAnnotation.valueField();
		this.configField = constraintAnnotation.configField();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		try {
			Boolean emiteNFCE = (Boolean) BeanUtils
					.getPropertyDescriptor(
						objetoValidacao.getClass(), 
						this.valueField)
					.getReadMethod().invoke(objetoValidacao);
			
			ConfiguracaoDocumentoFiscal document = 
					(ConfiguracaoDocumentoFiscal) BeanUtils
					.getPropertyDescriptor(
							objetoValidacao.getClass(), 
							this.configField)
						.getReadMethod().invoke(objetoValidacao);
			
			if(emiteNFCE) {
				return document != null && document.getConfigNFCE() != null;
			}
			return true;
		} catch (Exception e) {
			throw new ValidationException(e);
		} 		
	}

}
