package com.eauction.eauctionsellerservice.services;

import com.eauction.eauctionsellerservice.models.Bid;
import com.eauction.eauctionsellerservice.models.Product;
import com.eauction.eauctionsellerservice.repository.BidRepository;
import com.eauction.eauctionsellerservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BidRepository bidRepository;

    public static  final Logger log = LoggerFactory.getLogger(SellerService.class);

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    public List<Bid> getAllBids(String productId) {
        return bidRepository.findAllByProductId(productId);
    }

    public Optional<Product> getProduct(String productId) {
        return productRepository.findById(productId);
    }

}
