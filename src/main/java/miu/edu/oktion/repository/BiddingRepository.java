package miu.edu.oktion.repository;

import miu.edu.oktion.domain.Bidding;
import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.User;
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
