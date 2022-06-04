package br.com.intech.iseasypos.infrastructure.service.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import br.com.intech.iseasypos.core.storage.StorageProperties;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService;
import br.com.intech.iseasypos.infrastructure.exception.StorageException;

public class LocalArquivoStorageService implements ArquivoStorageService {
	@Autowired
	private StorageProperties storageProperties;
	@Override
	public void armazenar(NovoArquivo novoArquivo) {
		try {
			Path path = getArquivoPath(novoArquivo.getNome());
			FileCopyUtils.copy(novoArquivo.getInputStream(), Files.newOutputStream(path));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}
	}


	@Override
	public void remover(String nomeArquivo) {
		
		try {
			Path path = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
		
	}
	
	@Override
	public ArquivoRecuperado recuperar(String nomeArquivo) {
		try {
	        Path arquivoPath = getArquivoPath(nomeArquivo);
	        
	        ArquivoRecuperado arquivoRecuperado = ArquivoRecuperado.builder()
	        	.inputStream(Files.newInputStream(arquivoPath))
	        	.build();
	        
	        return arquivoRecuperado;
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
		
	}
	
	private Path getArquivoPath(String nome) {
		File dir = new File( storageProperties.getLocal().getDir().toString());
		if(!dir.exists()){
			dir.mkdir();
		}
		return storageProperties.getLocal().getDir().resolve(Paths.get(nome));
	}
}
