package br.com.intech.iseasypos.core.stone;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("speedcash.stone")
public class StoneProperties {
	private String code;
	private String link;
}
