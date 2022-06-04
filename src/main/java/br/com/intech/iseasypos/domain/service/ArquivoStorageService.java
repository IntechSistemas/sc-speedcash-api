package br.com.intech.iseasypos.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface ArquivoStorageService {
	public ArquivoRecuperado recuperar(String nomeArquivo);
	public void armazenar(NovoArquivo novoArquivo);
	public void remover(String nomeArquivo);
	
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + '-' + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NovoArquivo{
		private InputStream inputStream;
		private String  contentType;
		private String nome;
	}
	
	@Getter
	@Builder
	class ArquivoRecuperado{
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		public boolean temInputStream() {
			return inputStream != null;
		}
	}
}
