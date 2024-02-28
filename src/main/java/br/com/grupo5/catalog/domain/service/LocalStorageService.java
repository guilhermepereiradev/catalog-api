package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.core.StorageProperties;
import br.com.grupo5.catalog.domain.exception.BusinessRuleException;
import br.com.grupo5.catalog.domain.model.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor
public class LocalStorageService implements ImageStorage {

    private final StorageProperties storageProperties;

    @Override
    public void storagePicture(Picture picture, InputStream inputStream)  {
        Path path = Path.of(getFilePath(picture.getName()));
        try {
            FileCopyUtils.copy(inputStream, Files.newOutputStream(path));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessRuleException("Unable to upload file to Local Storage");
        }
    }

    @Override
    public String recoverPictureUrl(String fileName) {
        return getFilePath(fileName);
    }

    @Override
    public void removePictureFromStorage(String fileName) {
        Path path = Path.of(getFilePath(fileName));
        try{
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessRuleException("Unable to remove file from Local Storage");
        }
    }

    @Override
    public String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getLocal().getPicturesPath(), fileName);
    }
}
