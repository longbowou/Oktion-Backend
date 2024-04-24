package miu.edu.oktion.dto;

import lombok.Builder;
import lombok.Data;
import miu.edu.oktion.domain.Category;
import miu.edu.oktion.domain.ProductImage;
import miu.edu.oktion.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private double bidStartingPrice;
    private double price;
    private double deposit;
    private LocalDateTime bidDueDate;
    private LocalDateTime biddingPaymentDueDate;
    private double highestBidAmount = 0.0;
    private String status = "SaveWithoutRelease"; // SaveWithoutRelease, SaveAndRelease, Canceled, Sold

    private User seller;
    private User highestBidUser;

    private List<Category> categories = new ArrayList<>();

    private List<ProductImage> images = new ArrayList<>();
}
