package org.example.bll;

import org.example.dal.ProductDal;
import org.example.dal.ShoppingCart;
import org.example.dto.ProductDto;

public class CartManager {
    private ProductDal productDal;
    private ShoppingCart shoppingCart;

    public CartManager(ProductDal productDal, ShoppingCart shoppingCart) {
        this.productDal = productDal;
        this.shoppingCart = shoppingCart;
    }

    public void addProductToCart(int productId, int quantity) {
        ProductDto product = productDal.getProductById(productId);
        if (product != null) {
            shoppingCart.addProduct(product, quantity);
        }
    }
}