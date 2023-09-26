package com.curso.crud.service;

import com.curso.crud.entity.Product;
import com.curso.crud.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> listProduct(){
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(int id){
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public boolean existsProductById(int id){
        return productRepository.existsById(id);
    }

    public boolean existsProductByName(String name){
        return productRepository.existsByName(name);
    }
}
