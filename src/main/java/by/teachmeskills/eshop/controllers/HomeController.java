package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import static by.teachmeskills.eshop.PagesPathEnum.UPLOAD_PAGE;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView getHomePage(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = "6") int pageSize){
        return categoryService.getAllCategories(pageNumber, pageSize);
    }

    @GetMapping("/download")
    public void downloadCsvFile(HttpServletResponse response) {
        categoryService.writeCategoriesToCsv(response);
    }

    @GetMapping("/upload")
    public ModelAndView openUploadCategoriesPage() {
        return new ModelAndView(UPLOAD_PAGE.getPath());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<CategoryDto>> createCategory(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(categoryService.saveCategoriesFromFile(file), HttpStatus.CREATED);
    }
}
