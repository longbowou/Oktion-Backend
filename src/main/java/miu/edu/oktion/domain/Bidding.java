package miu.edu.oktion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
public class Bidding {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JsonIgnore
    private User customer; // customer only

    private double amount;

    private String status = "Running"; // Closed

    private LocalDateTime createdOn;
}
