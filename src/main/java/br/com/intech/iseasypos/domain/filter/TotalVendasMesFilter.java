package br.com.intech.iseasypos.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalVendasMesFilter {
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime de;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime ate;
	@JsonIgnore
	private Integer empresa;
}
