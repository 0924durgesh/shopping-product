package com.shoppingproduct.controller;


import com.shoppingproduct.dtos.ImageDto;
import com.shoppingproduct.exceptions.ResourceNotFoundException;
import com.shoppingproduct.model.Image;
import com.shoppingproduct.response.ApiResponse;
import com.shoppingproduct.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("{api-prefix}/images")   //time:1:43
public class ImageController {

    @Autowired
    private  ImageService imageService;
    @PostMapping("/upload")  //time :1:45
    public ResponseEntity<ApiResponse<AbstractResource>> saveImages(@RequestParam List<MultipartFile> files,
                                                                    @RequestParam Long productId) {
        try {
            List<ImageDto> savedImage = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse<>("Upload success", savedImage));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Upload failed", exception.getMessage()));
        }
    }

//    @GetMapping("/image/download/{imageId}")
//    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException {
//        Image image=imageService.getImageById(imageId);
//
//
//        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
//
//
//        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"").body(resource);
//    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            String file = null;
            SerialBlob blob = image.getImage(new SerialBlob(file.getBytes()));
            byte[] data = blob.getBytes(1, (int) blob.length()); // Blob index starts at 1
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile files,@PathVariable Long imageId)
    {   try
    {  Image image=imageService.getImageById(imageId);
        if(image!=null)
        {
            imageService.updateImage(files, imageId);
         return  ResponseEntity.ok(new ApiResponse("update success",files));
        }
    }catch (ResourceNotFoundException e)
    {return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
    }
  return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed",INTERNAL_SERVER_ERROR));
    }



    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@RequestBody MultipartFile files,@PathVariable Long imageId)
    {   try
    {  Image image=imageService.getImageById(imageId);
        if(image!=null)
        {
            imageService.updateImage(files, imageId);
            return  ResponseEntity.ok(new ApiResponse("Delete success",files));
        }
    }catch (ResourceNotFoundException e)
    {return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
    }
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed",INTERNAL_SERVER_ERROR));
    }
  }
