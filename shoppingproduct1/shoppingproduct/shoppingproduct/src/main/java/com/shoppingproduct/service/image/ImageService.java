package com.shoppingproduct.service.image;

import com.shoppingproduct.dtos.ImageDto;
import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Image;
import com.shoppingproduct.model.Product;
import com.shoppingproduct.repository.ImageRepository;
import com.shoppingproduct.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    @Autowired
    private  ImageRepository imageRepository;
    @Autowired
    private  ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Image Not Found with id->"+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->
                { throw new ResourceNotFoundException("No Image with id=" + id);
                });

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long ProductId) {
        Product product=productService.getProductById(ProductId);
          List<ImageDto> savedImageDto=new ArrayList<>();
          for(MultipartFile file:files) {

              try{
              Image image = new Image();
              image.setFileName(file.getOriginalFilename());
              image.setFileType(file.getContentType());
              image.setImage(new SerialBlob(file.getBytes()));
              image.setProduct(product);

              String buildDownloadUrl="api/v1/images/image/download/";
               String downloadUrl=buildDownloadUrl+image.getId();
              image.setDownloadUrl(downloadUrl);
              Image savedImage=imageRepository.save(image);

              savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
              imageRepository.save(savedImage);

             ImageDto imageDto=new ImageDto();
             imageDto.setImageId(savedImage.getId());
             imageDto.setImageName(savedImage.getName());
             imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                  savedImageDto.add(imageDto);
              }catch(Exception e)
              {
                  throw  new RuntimeException(e.getMessage());
              }


          }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
     try {
         Image image = getImageById(imageId);
         image.setFileName(file.getOriginalFilename());
         image.getImage(new SerialBlob(file.getBytes()));
         imageRepository.save(image);
     }
     catch (Exception exception)
     {
         throw  new RuntimeException(exception.getMessage());
     }
    }
}
