package miu.edu.auction.service.security;

import lombok.RequiredArgsConstructor;
import miu.edu.auction.domain.Role;
import miu.edu.auction.domain.User;
import miu.edu.auction.dto.DashboardDTO;
import miu.edu.auction.dto.RegistrationDTO;
import miu.edu.auction.dto.UserDTO;
import miu.edu.auction.dto.adapter.RegistrationAdapter;
import miu.edu.auction.repository.BiddingRepository;
import miu.edu.auction.repository.ProductRepository;
import miu.edu.auction.repository.RoleRepository;
import miu.edu.auction.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static miu.edu.auction.util.AppConstant.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final BiddingRepository biddingRepository;

    public User get(String id) {
        return userRepository.getById(id);
    }

    @Override
    public RegistrationDTO save(UserDTO u) {
        User user = new User();
        user.setEmail(u.getEmail());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        if (!u.getRoles().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            u.getRoles().forEach(role -> {
                // find the role
                Role roleE = roleRepository.findByRole(role.trim().toUpperCase());
                roles.add(roleE);
            });

            user.setRoles(roles);
        }
        user.setProducts(new ArrayList<>());


        user.setName(u.getName());
        //TODO send email activation code

        User newUser = userRepository.save(user);

        return RegistrationAdapter.getRegistrationDTOFromUser(newUser);
    }

    public DashboardDTO getDashboard(User user) {
        if (user.getRoles().stream().filter(role -> {
            return role.getRole().equals(ROLE_CUSTOMER);
        }).findFirst().isPresent()) {
            // dashboard for customer
            //TODO
                /*
                provide below info for customer
                -total number of bidding product
                -balance
                */

            int totalBiddingProduct = biddingRepository.countDistinctByCustomer(user);
            DashboardDTO dashboardDTO = new DashboardDTO(totalBiddingProduct, user.getBalance(), ROLE_CUSTOMER);
            return dashboardDTO;

        } else if (user.getRoles().stream().filter(role -> {
            return role.getRole().equals(ROLE_SELLER);
        }).findFirst().isPresent()) {
            /*
            Seller
                -total product count
                -balance
             */
            int totalProduct = productRepository.countProductBySeller(user);
            DashboardDTO dashboardDTO = new DashboardDTO(totalProduct, user.getBalance(), ROLE_SELLER);
            return dashboardDTO;
        }
        return new DashboardDTO(0, 0.0, ROLE_USER);
    }
}
