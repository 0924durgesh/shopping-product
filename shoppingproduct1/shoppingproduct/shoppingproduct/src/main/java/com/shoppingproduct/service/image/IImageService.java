package com.shoppingproduct.service.image;

import com.shoppingproduct.dtos.ImageDto;
import com.shoppingproduct.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long ProductId);
    void updateImage(MultipartFile file, Long imageId);

}
