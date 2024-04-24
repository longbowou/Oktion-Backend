package miu.edu.oktion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BiddingResponseDTO {
    private String id;
    private CustomerDTO customer; // customer only
    private double amount;

    private String status = "Running"; // Closed

    private LocalDateTime createdOn;
}
