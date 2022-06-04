package br.com.intech.iseasypos.domain.filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class FormaPagamentoFilter {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fim;
    String forma;
}
