package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;;
import static by.teachmeskills.eshop.PagesPathEnum.SEARCH_PAGE;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView openSearch() {
        return new ModelAndView(SEARCH_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView advancedSearch(@ModelAttribute SearchParamsDto searchParamsDto) {
        return productService.searchProducts(searchParamsDto);
    }
}
