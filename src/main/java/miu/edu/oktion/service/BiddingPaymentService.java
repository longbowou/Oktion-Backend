package miu.edu.oktion.service;

import miu.edu.oktion.domain.BiddingPayment;
import miu.edu.oktion.domain.User;

public interface BiddingPaymentService {

    public BiddingPayment getBiddingPaymentByUser(User customer);

    public BiddingPayment create(BiddingPayment biddingPayment);
}