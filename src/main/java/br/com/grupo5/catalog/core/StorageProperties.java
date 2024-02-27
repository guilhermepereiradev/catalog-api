package br.com.grupo5.catalog.core;

import com.amazonaws.regions.Regions;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("grupo5.storage")
public class StorageProperties {
    private S3 s3 = new S3();
    private StorageType storageType = StorageType.LOCAL;

    @Data
    public static class S3 {
        private String idAccessKey;
        private String secretAccessKey;
        private String bucket;
        private Regions region;
        private String picturesPath;
    }

    enum StorageType {
        LOCAL,
        S3
    }
}
