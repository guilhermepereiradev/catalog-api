package br.com.grupo5.catalog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class PictureRequest {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String description;
}
