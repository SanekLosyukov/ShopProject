package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Cart;
import org.springframework.web.servlet.ModelAndView;

public interface OrderService {
    ModelAndView createOrder(Cart cart);
    ModelAndView openOrder();
}
