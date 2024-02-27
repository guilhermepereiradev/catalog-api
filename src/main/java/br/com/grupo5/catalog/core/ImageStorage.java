package br.com.grupo5.catalog.core;

import br.com.grupo5.catalog.domain.model.Picture;

import java.io.InputStream;
import java.util.UUID;

public interface ImageStorage {
    void storagePicture(Picture picture, InputStream inputStream);

    String recoverPictureUrl(String fileName);

    void removePictureFromStorage(String fileName);

    String createFileName(UUID id, String fileName);

    String getFilePath(String fileName);
}
