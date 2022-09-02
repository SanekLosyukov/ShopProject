package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.ORDER_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.SEARCH_PAGE;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

//    @Operation(
//            summary = "Search product",
//            description = "Advanced search by category name, price and search key")
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Products found",
//                    content = @Content(schema = @Schema(implementation = ProductDto.class))
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Products not found"
//            )
//    })
//    @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            required = true,
//            description = "Search product",
//            content = @Content(schema = @Schema(implementation = SearchParamsDto.class))
//    )
    @PostMapping
    public ModelAndView advancedSearch(@ModelAttribute("search") SearchParamsDto search) {
        return productService.searchProducts(search);
    }
}
