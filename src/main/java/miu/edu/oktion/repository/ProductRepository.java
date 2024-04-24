package miu.edu.oktion.repository;

import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllBySellerAndStatusInOrderByCreatedOnDesc(User user, List<String> statusList);

    @Query("select p from  Product p where p.seller = :user and p.name like %:search% and p.status in :statusList or p.seller = :user and p.description like %:search% and p.status in :statusList order by p.createdOn desc")
    List<Product> filter(@Param("user") User user, @Param("search") String search, @Param("statusList") List<String> statusList);

    @Query("select p from  Product p where p.status = :status and p.name like %:search% or p.status = :status and p.description like %:search% order by p.createdOn desc")
    List<Product> filterByStatus(@Param("status") String status, @Param("search") String search);

    List<Product> findAllByStatus(String status);

    int countProductBySeller(User user);

    List<Product> findAllByHighestBidUserAndStatusInOrderByCreatedOnDesc(User user, List<String> statusList);

    List<Product> findAllByStatusAndBidDueDateBefore(String status, LocalDateTime bidDueDate);
}



