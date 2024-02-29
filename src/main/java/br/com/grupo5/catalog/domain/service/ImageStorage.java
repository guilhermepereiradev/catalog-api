package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.model.Picture;

import java.io.InputStream;

public interface ImageStorage {
    void storagePicture(Picture picture, InputStream inputStream);

    String recoverPictureUrl(String fileName);

    void removePictureFromStorage(String fileName);

    String getFilePath(String fileName);
}
