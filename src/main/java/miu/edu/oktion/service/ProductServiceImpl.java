package miu.edu.oktion.service;

import lombok.RequiredArgsConstructor;
import miu.edu.oktion.domain.Category;
import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.User;
import miu.edu.oktion.dto.ProductDTO;
import miu.edu.oktion.dto.ProductStatusDTO;
import miu.edu.oktion.repository.CategoryRepository;
import miu.edu.oktion.repository.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static miu.edu.oktion.util.AppConstant.*;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> getProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product getProduct(String id) {

        return productRepository.findById(id).get();
    }

    @Override
    public Product createProduct(Product product) {
//        ZoneId oldZone = ZoneId.of("UTC");
//        ZoneId newZone = ZoneId.of("America/Chicago");
//
//        LocalDateTime newBidDueDate = product.getBidDueDate().atZone(oldZone)
//                .withZoneSameInstant(newZone)
//                .toLocalDateTime();
//
//        LocalDateTime newBiddingPaymentDueDate = product.getBiddingPaymentDueDate().atZone(oldZone)
//                .withZoneSameInstant(newZone)
//                .toLocalDateTime();
//
//        product.setBidDueDate(newBidDueDate);
//        product.setBiddingPaymentDueDate(newBiddingPaymentDueDate);
        product.setCreatedOn(LocalDateTime.now());
        product.setDepositAmount(product.getPrice() * (product.getDeposit() / 100));
        setProductSellerAndCategory(product);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) throws Exception {
        Product productExisting = productRepository.findById(product.getId()).get();
        if (productExisting != null) {
            productExisting.setName(product.getName());
            productExisting.setDescription(product.getDescription());
            productExisting.setBidStartingPrice(product.getBidStartingPrice());
            productExisting.setPrice(product.getPrice());
            productExisting.setDeposit(product.getDeposit());
            productExisting.setDepositAmount(product.getPrice() * (product.getDeposit() / 100));
            productExisting.setBidDueDate(product.getBidDueDate());
            productExisting.setBiddingPaymentDueDate(product.getBiddingPaymentDueDate());

            productExisting.setStatus(product.getStatus());

            setProductSellerAndCategory(product);
            productExisting.setCategoryIds(product.getCategoryIds());

            return productRepository.save(productExisting);
        } else {
            throw new Exception("Unable to update product");
        }
    }

    /*
     * Set product seller and category
     */
    void setProductSellerAndCategory(Product product) {
        List<Category> categoryList = new ArrayList<>();

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*userRepository.findById(product.getSellerId()).get();*/
        product.setSeller(seller);

        for (String id : product.getCategoryIds()) {
            Category category = categoryRepository.findById(id).get();
            categoryList.add(category);
        }
        product.setCategories(categoryList);
    }

    //update status
    public void statusUpdate(Product product) {
        Product productExisting = productRepository.findById(product.getId()).get();
        ;
        if (productExisting != null) {
            productExisting.setStatus(product.getStatus());
            productRepository.save(productExisting);
        }
    }

    @Override
    public Boolean deleteProduct(Product product) {
        if (product != null) {
            product.setStatus(PRODUCT_DELETED_STATUS);
            productRepository.save(product);
            //productRepository.delete(product);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @Override

    public List<Product> getBiddingProducts(String status) {

        return productRepository.findAllByStatus(status);
    }

    @Override
    public List<Product> getProductBySellerV2(User user, String search) {

        if (search != null && !search.trim().isEmpty()) {
            return productRepository.filter(user, search, Arrays.asList(SAVE_WITHOUT_RELEASE, SAVE_AND_RELEASE, PRODUCT_CLOSED_STATUS, PRODUCT_SOLD_STATUS, PRODUCT_SOLD_AND_PAID_STATUS));
        }

        return productRepository.findAllBySellerAndStatusInOrderByCreatedOnDesc(user, Arrays.asList(SAVE_WITHOUT_RELEASE, SAVE_AND_RELEASE, PRODUCT_CLOSED_STATUS, PRODUCT_SOLD_STATUS, PRODUCT_SOLD_AND_PAID_STATUS));
    }


    @Deprecated
    public List<ProductDTO> getProductBySeller(User user) {
        List<Product> products = productRepository.findAllBySellerAndStatusInOrderByCreatedOnDesc(user, Arrays.asList(SAVE_WITHOUT_RELEASE, SAVE_AND_RELEASE, PRODUCT_CLOSED_STATUS, PRODUCT_SOLD_STATUS, PRODUCT_SOLD_AND_PAID_STATUS));
        List<ProductDTO> productDTOS = new ArrayList<>();

        products.stream().forEach(product -> productDTOS.add(ProductDTO.builder().id(product.getId()).seller(product.getSeller()).highestBidAmount(product.getHighestBidAmount()).highestBidUser(product.getHighestBidUser()).build()));
        return productDTOS;
    }

    @Override
    public Product updateReleaseStatus(ProductStatusDTO productStatusDTO, String id) {
        Product product = productRepository.findById(id).get();
        product.setStatus(productStatusDTO.getStatus());

        productRepository.save(product);
        return product;

    }

    @Override
    public List<Product> getProductsByStatus(String status) {

        return productRepository.findAllByStatus(status);
    }

    @Override
    public List<Product> getProductsByHighestBidWinner(User user, List<String> statusList) {
        return productRepository.findAllByHighestBidUserAndStatusInOrderByCreatedOnDesc(user, statusList);
    }

    @Override
    public List<Product> getCustomerProducts(User user, String status, String search) {
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productRepository.filterByStatus(status, search);
        } else {
            products = getProductsByStatus(status);
        }
        List<Product> winningProducts = getProductsByHighestBidWinner(user, Arrays.asList(PRODUCT_SOLD_STATUS, PRODUCT_SOLD_AND_PAID_STATUS));
        if (winningProducts != null) {
            products.addAll(winningProducts);
        }
        return products;
    }


    @Override
    public int totalProductBySeller(User user) {
        return productRepository.countProductBySeller(user);
    }

}

