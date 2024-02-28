package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.model.Picture;

import java.io.InputStream;
import java.util.UUID;

public interface ImageStorage {
    void storagePicture(Picture picture, InputStream inputStream);

    String recoverPictureUrl(String fileName);

    void removePictureFromStorage(String fileName);

    default String createFileName(UUID id, String fileName) {
        return String.format("%s_%s", id, fileName);
    }

    String getFilePath(String fileName);
}
