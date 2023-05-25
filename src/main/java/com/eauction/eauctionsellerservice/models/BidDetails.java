package com.eauction.eauctionsellerservice.models;


public class BidDetails {
    private Bid bid;
    private Product product;

    public BidDetails() {
    }

    public BidDetails(Bid bid, Product product) {
        this.bid = bid;
        this.product = product;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
