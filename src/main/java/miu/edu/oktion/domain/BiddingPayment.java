package miu.edu.oktion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

// Deposit amount of 10% & winning payment of the bid
@Entity
@Getter
@Setter
public class BiddingPayment {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private double amount; //deposit amount,

    private String status; // Deposit, Disbursed, WinningPayment

    private LocalDateTime createdOn;
}
