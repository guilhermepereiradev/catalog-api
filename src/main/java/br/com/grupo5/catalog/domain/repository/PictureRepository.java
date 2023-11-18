package br.com.grupo5.catalog.domain.repository;

import br.com.grupo5.catalog.domain.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, UUID> {

    @Query("from Picture p where p.product.id = :productId and p.id = :pictureId")
    Optional<Picture> findPictureById(UUID productId, UUID pictureId);
}
