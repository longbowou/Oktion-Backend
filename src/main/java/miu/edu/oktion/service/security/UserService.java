package miu.edu.oktion.service.security;


import miu.edu.oktion.domain.User;
import miu.edu.oktion.dto.DashboardDTO;
import miu.edu.oktion.dto.RegistrationDTO;
import miu.edu.oktion.dto.UserDTO;

public interface UserService {

    public User get(String id);

    public RegistrationDTO save(UserDTO u);

    public DashboardDTO getDashboard(User user);

}
