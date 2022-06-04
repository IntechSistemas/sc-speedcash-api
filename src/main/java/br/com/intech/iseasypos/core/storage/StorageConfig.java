package br.com.intech.iseasypos.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.intech.iseasypos.core.storage.StorageProperties.TipoStorage;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService;
import br.com.intech.iseasypos.infrastructure.service.storage.LocalArquivoStorageService;
import br.com.intech.iseasypos.infrastructure.service.storage.S3ArquivoStorageService;

@Configuration
public class StorageConfig {
	@Autowired
	private StorageProperties storageProperties;
	@Bean
	@ConditionalOnProperty(name = "speedcash.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				storageProperties.getS3().getAccessKey(),
				storageProperties.getS3().getSecret());
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
	};
	
	@Bean
	public ArquivoStorageService arquivoStorageService() {
		if(TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3ArquivoStorageService();
		}else {
			return new LocalArquivoStorageService();
		}
	}
}
