package com.grpc.rest.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grpc.microservice.Product;
import com.grpc.microservice.ProductList;
import com.grpc.rest.api.client.ProductClient;
import com.grpc.rest.api.models.ProductModel;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductClient grpcClient = new ProductClient();
 
    @GetMapping()
    public ResponseEntity<ArrayList<ProductModel>> getProducts(){
        try {
            ArrayList<ProductModel> response = new ArrayList<>();
            ProductList products = grpcClient.readProducts();
            List<Product> list = products.getProductsList(); 

            for (Product product: list) {
                ProductModel p = new ProductModel(
                    product.getId(), 
                    product.getDescription());  
                
                response.add(p);
            }

            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }  
    } 

    @PostMapping()
    @SuppressWarnings("rawtypes")
    public ResponseEntity postProduct(@RequestBody ProductModel product){
        try {
            grpcClient.createProduct(product.getDescription());
            return ResponseEntity.status(201).body(null);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping(path="/{id}")
    @SuppressWarnings("rawtypes")
    public ResponseEntity putProduct(
        @PathVariable("id") int id,
        @RequestBody ProductModel product){
        try {
            grpcClient.updateProduct(product.getDescription(), id);
            return ResponseEntity.status(200).body(null);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);

        }
    }

    @DeleteMapping(path="/{id}") 
    @SuppressWarnings("rawtypes")
    public ResponseEntity deleteProduct(@PathVariable("id") int id){
        try {
            grpcClient.deleteProduct(id);
            return ResponseEntity.status(200).body(null);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);

        }
    }
}
