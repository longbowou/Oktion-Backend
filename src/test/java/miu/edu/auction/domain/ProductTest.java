package miu.edu.auction.domain;

import miu.edu.auction.repository.CategoryRepository;
import miu.edu.auction.repository.ProductRepository;
import miu.edu.auction.service.ProductService;
import miu.edu.auction.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductTest {

    @Test
    public void testProductName() {
        Product product = new Product();
        product.setName("Test Product");
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testProductPrice() {
        Product product = new Product();
        product.setPrice(10.99);
        assertEquals(10.99, product.getPrice(), 0.01);
    }

    @Test
    public void testProductSave() {
        // Mocking a repository
        ProductRepository productRepository = mock(ProductRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(500);
        product.setBidDueDate(LocalDateTime.now().plusDays(5));
        product.setBiddingPaymentDueDate(LocalDateTime.now().plusDays(10));

        // Saving the product
        ProductService productService = new ProductServiceImpl(productRepository, categoryRepository);
        productService.createProduct(product);

        // Verifying that the save method of the repository was called with the correct product
        verify(productRepository, times(1)).save(product);
    }
}

