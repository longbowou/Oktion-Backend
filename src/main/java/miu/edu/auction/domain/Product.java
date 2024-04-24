package miu.edu.auction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @UuidGenerator()
    private String id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private double bidStartingPrice;
    private double price;
    private double deposit;
    private double depositAmount;
    private LocalDateTime bidDueDate;
    private LocalDateTime biddingPaymentDueDate;
    private String status = "Saved"; // Released, Closed, Sold, Sold & Paid

    @Transient
    private List<String> categoryIds = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User seller;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Bidding> bids = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    private double highestBidAmount = 0.0;

    @ManyToOne
    private User highestBidUser;

    private LocalDateTime createdOn;
}
