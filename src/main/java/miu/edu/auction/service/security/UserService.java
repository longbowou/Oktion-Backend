package miu.edu.auction.service.security;


import miu.edu.auction.domain.User;
import miu.edu.auction.dto.DashboardDTO;
import miu.edu.auction.dto.RegistrationDTO;
import miu.edu.auction.dto.UserDTO;

public interface UserService {

    public User get(String id);

    public RegistrationDTO save(UserDTO u);

    public DashboardDTO getDashboard(User user);

}
