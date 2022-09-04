package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.services.CustomUserDetailsService;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import java.util.Objects;
import static by.teachmeskills.eshop.EshopConstants.USER;
import static by.teachmeskills.eshop.PagesPathEnum.REGISTER_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.SIGN_IN_PAGE;

@RestController
@SessionAttributes({USER})
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(SIGN_IN_PAGE.getPath());
    }

    @GetMapping("/register")
    public ModelAndView openRegisterPage() {
        return new ModelAndView(REGISTER_PAGE.getPath());
    }

    @PostMapping("/register/save")
    public ModelAndView save(User user, ModelAndView modelAndView)  {
        modelAndView.setViewName(REGISTER_PAGE.getPath());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @ModelAttribute(USER)
    public User setUpUserForm() {
        return new User();
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
