package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import static by.teachmeskills.eshop.PagesPathEnum.UPLOAD2_PAGE;

@RestController
@RequestMapping("/product")
@Tag(name = "product", description = "The Product API")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView openProductPage(@PathVariable int id) {
        return productService.getProductData(id);
    }

    @GetMapping("/upload")
    public ModelAndView openUploadCategoriesPage() {
        return new ModelAndView(UPLOAD2_PAGE.getPath());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ProductDto>> createProduct(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(productService.saveProductsFromFile(file), HttpStatus.CREATED);
    }
}
