package miu.edu.auction.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.auction.domain.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String id;
    private String email;
    private String name;
    private List<Role> roles;
    private String accessToken;

}
