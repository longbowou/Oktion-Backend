package miu.edu.oktion.dto;

import lombok.Data;

/*
{
    "productId":2,
    "amount":20
}
 */
@Data
public class BiddingDTO {
    private String productId;
    private double amount;
}
