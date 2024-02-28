package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Picture;
import br.com.grupo5.catalog.domain.repository.PictureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository repository;

    private final ImageStorage storageService;

    @Transactional
    public Picture save(Picture picture, InputStream inputStream) {
        picture = repository.save(picture);

        var fileName = storageService.createFileName(picture.getId(), picture.getName());
        picture.setName(fileName);

        storageService.storagePicture(picture, inputStream);
        picture.setUrl(storageService.recoverPictureUrl(picture.getName()));

        return picture;
    }

    public Picture findById(UUID productId, UUID pictureId) {
        return repository.findPictureById(productId, pictureId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity not fount from product id = %s and picture id = %s", productId, pictureId)));
    }

    @Transactional
    public void delete(UUID productId, UUID pictureId) {
        var picture = findById(productId, pictureId);
        repository.delete(picture);
        storageService.removePictureFromStorage(picture.getName());
    }
}
