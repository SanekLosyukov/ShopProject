package by.teachmeskills.eshop.entities;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class Cart {
    private Map<Product, Integer> products;

    private int totalPrice = 0;

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product, product.getId());
        totalPrice += product.getPrice();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        totalPrice -= product.getPrice();
    }

    public void clear() {
        products.clear();
        totalPrice = 0;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
