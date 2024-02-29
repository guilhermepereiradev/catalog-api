package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PictureModel {

    private UUID id;
    private String name;
    private String description;
    private String url;
    private String contentType;
    private Long contentLength;


    public static PictureModel of(Picture picture){
        return new PictureModel(picture.getId(),
                picture.getName(),
                picture.getDescription(),
                picture.getUrl(),
                picture.getContentType(),
                picture.getSize());
    }
}
