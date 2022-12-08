package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import static by.teachmeskills.eshop.PagesPathEnum.UPLOAD2_PAGE;

@Validated
@Tag(name = "category", description = "The Category API")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ProductService productService;
    private final CategoryService categoryService;
    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("{categoryName}/{categoryId}")
    public ModelAndView openCategoryProductPage(@PathVariable String categoryName,
                                                @PathVariable int categoryId,
                                                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return productService.getCategoryProductsData(categoryId, categoryName, pageNumber, pageSize);
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
        CategoryDto categoryDto = categoryService.getCertainCategory(id);
        if (Optional.ofNullable(categoryDto).isPresent()) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto created = categoryService.createCategory(categoryDto);
        if (Optional.ofNullable(created).isPresent()) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/upload")
    public ModelAndView openUploadCategoriesPage() {
        return new ModelAndView(UPLOAD2_PAGE.getPath());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ProductDto>> createProduct(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(productService.saveProductsFromFile(file), HttpStatus.CREATED);
    }

    @GetMapping("/download")
    public void downloadCsvFile(HttpServletResponse response, @RequestParam("categoryId") int id){
        productService.writeProductsCategoryToCsv(id, response);
    }
}
