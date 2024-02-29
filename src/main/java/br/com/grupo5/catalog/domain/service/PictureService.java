package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Picture;
import br.com.grupo5.catalog.domain.model.Product;
import br.com.grupo5.catalog.domain.repository.PictureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository repository;

    private final ImageStorage storageService;

    @Transactional
    public Picture save(String description, MultipartFile file, Product product) throws IOException {
        var uuid = UUID.randomUUID();
        var fileName = String.format("%s_%s", file.getOriginalFilename(), uuid);

        var picture = Picture.builder()
                .id(uuid)
                .name(fileName)
                .description(description)
                .contentType(file.getContentType())
                .size(file.getSize())
                .product(product)
                .build();

        storageService.storagePicture(picture, file.getInputStream());
        picture.setUrl(storageService.recoverPictureUrl(picture.getName()));

        return repository.save(picture);
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
