package br.com.intech.iseasypos.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.intech.iseasypos.core.storage.StorageProperties;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService;
import br.com.intech.iseasypos.infrastructure.exception.StorageException;

public class S3ArquivoStorageService implements ArquivoStorageService {
	@Autowired
	private AmazonS3 amazonS3;
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public ArquivoRecuperado recuperar(String nomeArquivo) {
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo));
		
		return ArquivoRecuperado.builder()
				.url(url.toString())
				.build();
	}

	@Override
	public void armazenar(NovoArquivo novoArquivo) {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novoArquivo.getContentType());

			PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(),
					getCaminhoArquivo(novoArquivo.getNome()), novoArquivo.getInputStream(), objectMetadata)
							.withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
		}

	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
					caminhoArquivo);

			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
		}

	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDir(), nomeArquivo);
	}

}
