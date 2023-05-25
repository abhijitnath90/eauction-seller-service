package com.eauction.eauctionsellerservice.repository;

import com.eauction.eauctionsellerservice.models.Bid;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository {

    List<Bid> findAllByProductId(String productId);

    Optional<Bid> findByProductIdAndEmail(String productId, String emailId);

}
