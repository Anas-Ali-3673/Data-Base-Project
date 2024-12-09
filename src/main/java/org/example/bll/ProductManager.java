package org.example.bll;

import org.example.dal.ProductDal;
import org.example.dto.ProductDto;

import java.util.List;

public class ProductManager {
    private  ProductDal productDal;

    public ProductManager(ProductDal productDal) {
        this.productDal = productDal;
    }

    public boolean addProduct(ProductDto product) {
        return productDal.addProduct(product);
    }

    public boolean updateProduct(ProductDto product) {
        return productDal.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return productDal.deleteProduct(productId);
    }

    public List<ProductDto> getAllProducts() {
        return productDal.getAllProducts();
    }

    public List<ProductDto> searchProducts(String name, String category, Double minPrice, Double maxPrice) {
        return productDal.searchProducts(name, category, minPrice, maxPrice);
    }
    public ProductDto getProductById(int productId) {
        return productDal.getProductById(productId);
    }
}