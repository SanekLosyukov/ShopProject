package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductService extends BaseService<Product>{
    List<Product> getAllForCategory(int categoryId);
    ModelAndView getCategoryProductsData(int id, String nameCategory, int pageNumber, int pageSize);
    List<Product> getAllByCategoryAndNamePaged(int categoryId, String title, int pageNumber, int pageSize);
    ModelAndView getProductData(int id);
    ModelAndView searchProducts(SearchParamsDto searchParamsDto, int pageNumber, int pageSize);
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> saveProductsFromFile(MultipartFile file) throws Exception;
    void writeProductsCategoryToCsv(int categoryId, HttpServletResponse response);
}
