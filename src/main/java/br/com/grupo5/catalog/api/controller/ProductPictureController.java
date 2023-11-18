package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.PictureModel;
import br.com.grupo5.catalog.domain.model.Picture;
import br.com.grupo5.catalog.domain.service.PictureService;
import br.com.grupo5.catalog.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products/{productId}/pictures")
@RequiredArgsConstructor
public class ProductPictureController {

    private final ProductService productService;

    private final PictureService pictureService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PictureModel> create(@PathVariable UUID productId,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("description") String description) throws IOException {
        var product = productService.findById(productId);

        var picture = new Picture();
        picture.setName(file.getOriginalFilename());
        picture.setDescription(description);
        picture.setContentType(file.getContentType());
        picture.setSize(file.getSize());
        picture.setProduct(product);

        picture = pictureService.save(picture, file.getInputStream());

        return ResponseEntity.ok(PictureModel.toDto(picture));
    }

    @GetMapping
    public ResponseEntity<List<PictureModel>> findAllByProductId(@PathVariable UUID productId) {
        var product = productService.findById(productId);
        var picturesModel = product.getPictures().stream().map(PictureModel::toDto).toList();

        return ResponseEntity.ok(picturesModel);
    }

    @GetMapping("/{pictureId}")
    public ResponseEntity<PictureModel> findById(@PathVariable UUID productId, @PathVariable UUID pictureId) {
        var picture = pictureService.findById(productId, pictureId);
        return ResponseEntity.ok(PictureModel.toDto(picture));
    }

    @DeleteMapping("/{pictureId}")
    public ResponseEntity<Void> delete(@PathVariable UUID productId, @PathVariable UUID pictureId) {
        pictureService.delete(productId, pictureId);
        return ResponseEntity.noContent().build();
    }
}
