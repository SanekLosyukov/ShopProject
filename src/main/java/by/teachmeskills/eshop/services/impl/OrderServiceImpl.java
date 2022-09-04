package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.RequestParamsEnum;
import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderDetailsRepository;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;
import java.util.Optional;
import static by.teachmeskills.eshop.PagesPathEnum.ORDER_PAGE;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public ModelAndView createOrder(Cart cart) {
        ModelMap modelParams = new ModelMap();
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findUserByName(loggedUser);;
        Order order = Order.builder()
                .user(user.get())
                .date(LocalDateTime.now())
                .price(cart.getTotalPrice())
                .products(cart.getProducts())
                .build();
        orderRepository.save(order);
        cart.clear();
        return new ModelAndView( "redirect:/home");
    }

    @Override
    public ModelAndView openOrder() {
        ModelMap modelParams = new ModelMap();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> searchedUser = userRepository.findUserByName(userName);
        modelParams.addAttribute(RequestParamsEnum.USER.getValue(), searchedUser.get());
        return new ModelAndView(ORDER_PAGE.getPath(), modelParams);
    }
}