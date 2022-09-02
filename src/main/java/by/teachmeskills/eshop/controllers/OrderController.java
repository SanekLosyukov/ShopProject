package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.PagesPathEnum;
import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.EshopConstants.PRODUCT_ID_PARAM;
import static by.teachmeskills.eshop.EshopConstants.SHOPPING_CART;
import static by.teachmeskills.eshop.EshopConstants.USER;
import static by.teachmeskills.eshop.PagesPathEnum.ORDER_PAGE;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //    @GetMapping("/create")
//    public void createOrder(@RequestParam(PRODUCT_ID_PARAM) String id) {
//        int productId = Integer.parseInt(id);
//        orderService.createOrder(productId);
//    }
    @GetMapping("/create")
    public ModelAndView createOrder(@SessionAttribute(SHOPPING_CART) Cart cart) {
        return orderService.createOrder(cart);
    }

    @GetMapping("/open")
    public ModelAndView openOrderPage() {

        return orderService.openOrder();
    }

    //        return new ModelAndView(ORDER_PAGE.getPath());
}
