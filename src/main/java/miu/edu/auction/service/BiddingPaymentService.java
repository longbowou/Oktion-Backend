package miu.edu.auction.service;

import miu.edu.auction.domain.BiddingPayment;
import miu.edu.auction.domain.User;

public interface BiddingPaymentService {

    public BiddingPayment getBiddingPaymentByUser(User customer);

    public BiddingPayment create(BiddingPayment biddingPayment);
}