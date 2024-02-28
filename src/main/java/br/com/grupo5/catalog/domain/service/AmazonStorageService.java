package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.core.StorageProperties;
import br.com.grupo5.catalog.domain.exception.BusinessRuleException;
import br.com.grupo5.catalog.domain.model.Picture;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class AmazonStorageService implements ImageStorage {

    private final AmazonS3 amazonS3;

    private final StorageProperties storageProperties;

    @Override
    public void storagePicture(Picture picture, InputStream inputStream) {
        try {
            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(picture.getContentType());
            objectMetaData.setContentLength(picture.getSize());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    getFilePath(picture.getName()),
                    inputStream,
                    objectMetaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessRuleException("Unable to upload file to Amazon Storage");
        }
    }

    @Override
    public String recoverPictureUrl(String fileName) {
        return amazonS3.getUrl(storageProperties.getS3().getBucket(), getFilePath(fileName)).toString();
    }

    @Override
    public void removePictureFromStorage(String fileName) {
        try {
            var filePath = getFilePath(fileName);
            var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), filePath);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessRuleException("Unable to remove file to Amazon Storage");
        }
    }

    @Override
    public String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getPicturesPath(), fileName);
    }
}
