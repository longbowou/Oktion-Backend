package miu.edu.oktion.service;


import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.User;
import miu.edu.oktion.dto.ProductStatusDTO;

import java.util.List;

public interface ProductService {
    public List<Product> getProducts();

    public Product getProduct(String id);

    public Product createProduct(Product product);

    public Product updateProduct(Product product) throws Exception;

    public Boolean deleteProduct(Product product);

    List<Product> getBiddingProducts(String status);

    public List<Product> getProductBySellerV2(User user, String search);

    public Product updateReleaseStatus(ProductStatusDTO productStatusDTO, String id);

    public List<Product> getProductsByStatus(String status);

    public List<Product> getProductsByHighestBidWinner(User user, List<String> statusList);

    public List<Product> getCustomerProducts(User user, String status, String search);

    public int totalProductBySeller(User user);

}
