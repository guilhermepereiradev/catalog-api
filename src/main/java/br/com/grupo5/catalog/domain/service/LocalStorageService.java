package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.core.ImageStorage;
import br.com.grupo5.catalog.core.StorageProperties;
import br.com.grupo5.catalog.domain.model.Picture;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.UUID;

/**
 * TODO
 *  implementar armazenamento de imagem local
 */
@RequiredArgsConstructor
public class LocalStorageService implements ImageStorage {

    private final StorageProperties storageProperties;

    @Override
    public void storagePicture(Picture picture, InputStream inputStream) {}

    @Override
    public String recoverPictureUrl(String fileName) {
        return "";
    }

    @Override
    public void removePictureFromStorage(String fileName) {}

    @Override
    public String createFileName(UUID id, String fileName) {
        return "";
    }

    @Override
    public String getFilePath(String fileName) {
        return "";
    }
}
