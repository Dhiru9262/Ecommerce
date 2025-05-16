package com.dhiru.ecom_proj.controller;

import com.dhiru.ecom_proj.model.Product;
import com.dhiru.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class ProductController
{
    @Autowired
    private ProductService service;

@RequestMapping("/")
public String greet (){
    return "Hello World";
}

@GetMapping("/products")
public  ResponseEntity<List<Product>> getAllProduct(){
    return new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);
}

@GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
    return new ResponseEntity<>(service.getProductId(id), HttpStatus.OK);
}

@PostMapping("/product")
public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){

  try {
      Product product1 = service.addProduct(product, imageFile);
      return new ResponseEntity<>(product1,HttpStatus.CREATED);
  } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

@GetMapping("/product/{productId}/image")
public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
    Product product  = service.getProductId(productId);

    byte[] imageFile = product.getImageDate();

    return ResponseEntity.ok().body(imageFile);
}

@PutMapping("/product/{Id}")
public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {

   Product product1 = service.updateProduct(id,product,imageFile);
   if (product1 !=null ){
       return new ResponseEntity<>("Updated" , HttpStatus.OK);
   }else {
       return new ResponseEntity<>("fail to update",HttpStatus.BAD_REQUEST);
   }
}

@DeleteMapping("/product/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable int id){
    Product product = service.getProductId(id);
    if (product != null){
     service.deleteProduct(id);
    return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Product not found" , HttpStatus.NOT_FOUND);
    }
}

@GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
    List<Product> product = service.searchProducts(keyword);
    return new ResponseEntity<>(product,HttpStatus.OK);
}

}
