package miu.edu.oktion;

import miu.edu.oktion.domain.Category;
import miu.edu.oktion.domain.Role;
import miu.edu.oktion.domain.User;
import miu.edu.oktion.repository.CategoryRepository;
import miu.edu.oktion.repository.RoleRepository;
import miu.edu.oktion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuctionDataLoader implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public AuctionDataLoader() {
    }

    public void run(ApplicationArguments args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // roles
        Role roleCustomer = new Role();
        roleCustomer = roleRepository.findByRole("CUSTOMER");
        if (roleCustomer == null) {
            roleCustomer = new Role();
            roleCustomer.setRole("CUSTOMER");
            roleRepository.save(roleCustomer);
        }
        Role roleSeller = new Role();
        roleSeller = roleRepository.findByRole("SELLER");
        if (roleSeller == null) {
            roleSeller = new Role();
            roleSeller.setRole("SELLER");
            roleRepository.save(roleSeller);
        }

        //users
        if (userRepository.findByEmail("seller@oktion.app").isEmpty()) {
            User user1 = new User();
            user1.setName("Seller Oktion");
            user1.setEmail("seller@oktion.app");
            user1.setPassword(bCryptPasswordEncoder.encode("sellerpassword"));
            user1.setRoles(List.of(roleSeller));

            userRepository.save(user1);
        }

        if (userRepository.findByEmail("seller1@oktion.app").isEmpty()) {
            User user1 = new User();
            user1.setName("Seller One Oktion");
            user1.setEmail("seller1@oktion.app");
            user1.setPassword(bCryptPasswordEncoder.encode("seller1password"));
            user1.setRoles(List.of(roleSeller));

            userRepository.save(user1);
        }

        if (userRepository.findByEmail("john@oktion.app").isEmpty()) {
            User user1 = new User();
            user1.setName("John Doe");
            user1.setEmail("john@oktion.app");
            user1.setPassword(bCryptPasswordEncoder.encode("johnpassword"));
            user1.setRoles(List.of(roleCustomer));

            userRepository.save(user1);
        }

        if (userRepository.findByEmail("jane@oktion.app").isEmpty()) {
            User user1 = new User();
            user1.setName("Jane Doe");
            user1.setEmail("jane@oktion.app");
            user1.setPassword(bCryptPasswordEncoder.encode("janepassword"));
            user1.setRoles(List.of(roleCustomer));

            userRepository.save(user1);
        }

        // category
        if (categoryRepository.findByName("Antique").isEmpty()) {
            Category category1 = new Category();
            category1.setName("Antique");
            categoryRepository.save(category1);
        }

        if (categoryRepository.findByName("Art").isEmpty()) {
            Category category2 = new Category();
            category2.setName("Art");
            categoryRepository.save(category2);
        }

        if (categoryRepository.findByName("Jewelry").isEmpty()) {
            Category category3 = new Category();
            category3.setName("Jewelry");
            categoryRepository.save(category3);
        }

        if (categoryRepository.findByName("Electronics").isEmpty()) {
            Category category4 = new Category();
            category4.setName("Electronics");
            categoryRepository.save(category4);
        }
    }
}