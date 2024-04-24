package miu.edu.auction.domain.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;

}
