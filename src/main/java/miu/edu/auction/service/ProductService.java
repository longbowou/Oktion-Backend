package miu.edu.auction.service;


import miu.edu.auction.domain.Product;
import miu.edu.auction.domain.User;
import miu.edu.auction.dto.ProductStatusDTO;

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
