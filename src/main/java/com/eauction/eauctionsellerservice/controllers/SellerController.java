package com.eauction.eauctionsellerservice.controllers;

import com.eauction.eauctionsellerservice.exception.ResourceNotFoundException;
import com.eauction.eauctionsellerservice.models.Bid;
import com.eauction.eauctionsellerservice.models.BidDetails;
import com.eauction.eauctionsellerservice.models.Product;
import com.eauction.eauctionsellerservice.services.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/e-auction/api/v1/seller")
@CrossOrigin(origins = "*")
public class SellerController {

    @Autowired
    SellerService sellerService;

    public static final Logger log = LoggerFactory.getLogger(SellerController.class);

    @PostMapping(value = "/add-product",
                consumes = "application/json",
                produces = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            if(!product.getCategory().equals("Painting") && !product.getCategory().equals("Car")
               && !product.getCategory().equals("Watch")) {
                throw new RuntimeException("Product category should be Painting or Car or Watch");
            }
            Product newProduct = new Product(product.getId(), product.getProductName(), product.getShortDescription(), product.getDetailedDescription(),
                    product.getCategory(), product.getStartingPrice(), product.getBidEndDate(), product.getSeller());

            Product addedProduct = sellerService.saveProduct(newProduct);
            log.info("Product has been added: {}", addedProduct);
            return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
        } catch(Exception e) {
            log.error("Product not added:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        try {
            Optional<Product> product = sellerService.getProduct(productId);
            if(!product.isPresent()) {
                throw new ResourceNotFoundException("Cannot find product with given ID");
            }
            if(new Date().after(product.get().getBidEndDate())) {
                throw new ResourceNotFoundException("Product cannot be deleted after bid end date");
            }
            List<Bid> bidData = sellerService.getAllBids(productId);

            if(bidData != null && bidData.size()>0) {
                throw new RuntimeException("Product cannot be deleted as it has bid/bids placed on the product");
            }
            sellerService.deleteProduct(productId);
            log.info("Deleted product successfully");
            return  ResponseEntity.ok().body("Deleted product with ID: "+productId+" successfully.");
        } catch(Exception e) {
            log.error("Error in deleting product with ID: "+productId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/show-bids/{productId}")
    public ResponseEntity<?> showBid(@PathVariable String productId) throws ResourceNotFoundException{
        List<Bid> bidData = sellerService.getAllBids(productId);
        List<BidDetails> bidDetailsList = new ArrayList<>();

        if(bidData != null && bidData.size() > 0) {
            log.info("Fetched bid details successfully");
            for(Bid bid : bidData) {
                BidDetails bidDetails = new BidDetails();
                Optional<Product> product = sellerService.getProduct(bid.getProductId());
                if (!product.isPresent()) {
                    throw new ResourceNotFoundException("Cant find any product under given ID");
                }
                bidDetails.setBid(bid);
                bidDetails.setProduct(product.get());
                bidDetailsList.add(bidDetails);
            }
            return ResponseEntity.ok().body(bidDetailsList);
        }
        else {
            log.error("Error in fetching bid details for the product:",productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
