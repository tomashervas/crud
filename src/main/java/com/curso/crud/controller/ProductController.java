package com.curso.crud.controller;

import com.curso.crud.dto.Message;
import com.curso.crud.dto.ProductDto;
import com.curso.crud.entity.Product;
import com.curso.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProductoService productoService;

    @GetMapping()
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok(productoService.listProduct());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id){
        if(!productoService.existsProductById(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoService.getProduct(id).get());
    }

    @GetMapping("/detailbyname/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name){
        if(!productoService.existsProductByName(name)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoService.getProductByName(name).get());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        if(StringUtils.isBlank(productDto.getName())){
            return ResponseEntity.badRequest().build();
        }
        if(productDto.getPrice() == null || productDto.getPrice() < 0){
            return ResponseEntity.badRequest().build();
        }
        if(productoService.existsProductByName(productDto.getName())){
            return new ResponseEntity(new Message("El producto ya existe"), HttpStatus.BAD_REQUEST);
        }
        Product product = new Product(productDto.getName(), productDto.getPrice());
        productoService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody ProductDto productDto){
        if(!productoService.existsProductById(id)){
            return ResponseEntity.notFound().build();
        }
        if(StringUtils.isBlank(productDto.getName())){
            return ResponseEntity.badRequest().build();
        }
        if(productDto.getPrice() == null || productDto.getPrice() < 0){
            return ResponseEntity.badRequest().build();
        }
        Product product = productoService.getProduct(id).get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        productoService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){
        if(!productoService.existsProductById(id)){
            return ResponseEntity.notFound().build();
        }
        productoService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }








}
