package miu.edu.auction.repository;

import miu.edu.auction.domain.BiddingPayment;
import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiddingPaymentRepository extends JpaRepository<BiddingPayment, String> {


    BiddingPayment findBiddingPaymentByUser(User customer);

    BiddingPayment findBiddingPaymentByUserAndProductAndStatus(User customer, Product product, String status);

    List<BiddingPayment> findAllByProductAndStatus(Product product, String status);
}
