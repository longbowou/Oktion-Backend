package miu.edu.auction.repository;

import miu.edu.auction.domain.Bidding;
import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingRepository extends JpaRepository<Bidding, String> {
    int countDistinctByCustomer(User customer);
//    Integer depositAmountByCustomer(User customer);

    Bidding getBiddingByCustomerId(String customerId);

    List<Bidding> findBiddingByProductAndCustomer(Product product, User customer);

    int countBiddingByProductAndCustomer(Product product, User customer);

    List<Bidding> findAllByProductOrderByCreatedOnDesc(Product product);

    Bidding findTopByProductAndCustomerOrderByAmountDesc(Product product, User customer);

}
