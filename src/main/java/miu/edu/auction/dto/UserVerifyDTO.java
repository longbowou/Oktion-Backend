package miu.edu.auction.dto;

import lombok.Data;

@Data
public class UserVerifyDTO {
    private String email;
    private Boolean verified = Boolean.FALSE;
    private String status;
}
