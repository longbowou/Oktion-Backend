package miu.edu.oktion.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.oktion.domain.Product;
import miu.edu.oktion.domain.ProductImage;
import miu.edu.oktion.dto.ProductStatusDTO;
import miu.edu.oktion.exception.ApiRequestException;
import miu.edu.oktion.repository.ProductImageRepository;
import miu.edu.oktion.service.FilesStorageService;
import miu.edu.oktion.service.ProductService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static miu.edu.oktion.util.AppConstant.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final FilesStorageService filesStorageService;
    private final ProductImageRepository productImageRepository;
    private final String status = SAVE_WITHOUT_RELEASE; // Initialize the 'status' variable

    @GetMapping
    public ResponseEntity<?> getProductsBySeller(@RequestParam(value = "search", required = false) String search) {
        try {
            List<Product> products = productService.getProductBySellerV2(getUser(), search);
            return getResponse(SUCCESS, products, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Unable to get products", ex);
            return getResponse(ERROR, "Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducts(@PathVariable("id") String id) {
        try {
            return getResponse(SUCCESS, productService.getProduct(id), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to get product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product productDTO) {
        Product result = null;
        try {
            result = productService.createProduct(productDTO);
            return getResponse(SUCCESS, result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "{id}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
        Product result = null;
        try {
            Product product = productService.getProduct(id);
            String filename = filesStorageService.save(file);
            ProductImage image = new ProductImage();
            image.setPath(filename);
            image.setProduct(product);
            productImageRepository.save(image);

            return getResponse(SUCCESS, image, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to upload images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Product productDTO) {
        Product result = null;
        try {
            result = productService.updateProduct(productDTO);
            return getResponse(SUCCESS, result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to update product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Customer product list (released and winning items)
     */
    @GetMapping("/bidding")
    public ResponseEntity<?> getBiddingProduct(@RequestParam(value = "status", required = false) String status) {
        try {
            return getResponse(SUCCESS, productService.getProductsByStatus(status), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to get products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateReleaseStatus(@PathVariable("id") String id, @RequestBody ProductStatusDTO productStatusDTO) {
        Product product = productService.getProduct(id);
        try {
            if (product.getStatus().equals(status) && SAVE_AND_RELEASE.equals(productStatusDTO.getStatus())) {
                return getResponse(SUCCESS, productService.updateReleaseStatus(productStatusDTO, id), HttpStatus.OK);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        throw new ApiRequestException("Unable to update product status", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String productId) {
        //TODO check if any bid available before deleting product
        Product product = productService.getProduct(productId);
        if (product == null) {
            throw new ApiRequestException("Unable to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (product.getStatus().equals(status)) {
            try {
                boolean deleted = productService.deleteProduct(product);
                if (!deleted) {
                    throw new ApiRequestException("Unable to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return getResponse(SUCCESS, product, HttpStatus.OK);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new ApiRequestException("Unable to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new ApiRequestException("Unable to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}




