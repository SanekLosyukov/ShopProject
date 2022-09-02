package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static by.teachmeskills.eshop.EshopConstants.SHOPPING_CART;
import static by.teachmeskills.eshop.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.eshop.RequestParamsEnum.PRODUCT;


@Service
public class CartService {
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(shopCart::addProduct);
        modelParams.addAttribute(PRODUCT.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

//    public ModelAndView removeProductFromShoppingCart(int productId, Cart shopCart) throws Exception {
//        ModelMap modelParams = new ModelMap();
//        shopCart.removeProduct(productId);
//        modelParams.addAttribute(SHOPPING_CART, shopCart);
//        return new ModelAndView(CART_PAGE.getPath(), modelParams);
//    }
}
