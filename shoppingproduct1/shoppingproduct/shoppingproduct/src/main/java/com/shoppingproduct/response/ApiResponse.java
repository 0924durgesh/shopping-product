package com.shoppingproduct.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.AbstractResource;

@Data

@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getter and Setter
}

//public class ApiResponse<B extends AbstractResource> {
//    private String message;
//    private Object  data;

//    public class ApiResponse<T> {
//        private String message;
//        private T data;
//
//        public ApiResponse(String message, T data) {
//            this.message = message;
//            this.data = data;
//        }
//
//        // Getter and Setter
//    }
//
//    public ApiResponse(String uploadFailed) {
//    }
//}
