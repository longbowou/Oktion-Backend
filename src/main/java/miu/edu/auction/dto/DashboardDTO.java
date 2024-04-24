package miu.edu.auction.dto;

import lombok.Getter;

/*
provide below info for customer
                -total number of bidding product
                -balance
 */
@Getter
public class DashboardDTO {
    private int totalNumberOfProduct = 0;
    private double balance;
    private String type;

    public DashboardDTO(int totalNumberOfProduct, double balance, String type) {
        this.totalNumberOfProduct = totalNumberOfProduct;
        this.balance = balance;
        this.type = type;
    }
}
