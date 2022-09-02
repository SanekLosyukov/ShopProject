package by.teachmeskills.eshop.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Cart {
    //    private Map<Integer, Product> products;
//    private int totalPrice = 0;
//
//    public Cart() {
//        this.products = new HashMap<>();
//    }
//
//    public void addProduct(Product product) {
//        products.put(product.getId(), product);
//        totalPrice += product.getPrice();
//    }
//
//    public void removeProduct(int productId) {
//        Product product = products.get(productId);
//        products.remove(productId);
//        totalPrice -= product.getPrice();
//    }
//
//    public List<Product> getProducts() {
//        return new ArrayList<>(products.values());
//    }
//
//    public void clear() {
//        products.clear();
//    }
//
//}
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
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
