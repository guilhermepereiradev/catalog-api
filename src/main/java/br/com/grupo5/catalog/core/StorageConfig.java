package br.com.grupo5.catalog.core;

import br.com.grupo5.catalog.domain.service.AmazonStorageService;
import br.com.grupo5.catalog.domain.service.ImageStorage;
import br.com.grupo5.catalog.domain.service.LocalStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {
        var s3 = storageProperties.getS3();
        if (s3.getIdAccessKey() == null || s3.getSecretAccessKey() == null || s3.getRegion() == null) {
            log.info("Amazon S3 credentials not populated");
            return null;
        }

        var credentials = new BasicAWSCredentials(s3.getIdAccessKey(), s3.getSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(s3.getRegion())
                .build();
    }

    @Bean
    public ImageStorage imageStorage() {
        if (storageProperties.getStorageType().equals(StorageProperties.StorageType.S3)) {
            return new AmazonStorageService(amazonS3(), storageProperties);
        }

        return new LocalStorageService(storageProperties);
    }

}