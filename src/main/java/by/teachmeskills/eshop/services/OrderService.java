package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.web.servlet.ModelAndView;

public interface OrderService {
    ModelAndView createOrder(Cart cart);
    ModelAndView openOrder();
}
