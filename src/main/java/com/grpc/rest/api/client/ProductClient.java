package com.grpc.rest.api.client;

import org.springframework.stereotype.Service;

import com.grpc.microservice.NoParam;
import com.grpc.microservice.Product;
import com.grpc.microservice.ProductDescription;
import com.grpc.microservice.ProductId;
import com.grpc.microservice.ProductList;
import com.grpc.microservice.ProductServiceGrpc;
import com.grpc.microservice.Result;
import com.grpc.microservice.ProductServiceGrpc.ProductServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class ProductClient {

    ManagedChannel channel = ManagedChannelBuilder
                                .forAddress("localhost", 4001)
                                .usePlaintext()
                                .build(); 
    
    private ProductServiceBlockingStub productStub = ProductServiceGrpc
                                                .newBlockingStub(channel);
    
    public ProductList readProducts(){
        NoParam request = NoParam.newBuilder().build();
        return productStub.readProduct(request);
    }

    public Result createProduct(String description) {
        ProductDescription request = ProductDescription.newBuilder()
                                                .setDescription(description)
                                                .build();
        return productStub.createProduct(request);
    }

    public Result updateProduct(String description, int id) {
        Product request = Product.newBuilder()
                                .setDescription(description)
                                .setId(id)
                                .build();

        return productStub.updateProduct(request);
    }

    public Result deleteProduct(int id) {
        ProductId request = ProductId.newBuilder()
                                    .setId(id)
                                    .build();

        return productStub.deleteProduct(request);
    }
}
