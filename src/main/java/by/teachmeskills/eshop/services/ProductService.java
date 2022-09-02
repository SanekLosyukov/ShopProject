package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseService<Product>{
    List<Product> getAllForCategory(int categoryId);
    List<Product> getAllForCategoryPaged(int categoryId, int pageNumber, int pageSize);
    List<Product> getAllByCategoryAndNamePaged(int categoryId, String title, int pageNumber, int pageSize);
    List<Product> searchProducts(String key, String value);
    ModelAndView getProductData(int id);
    ModelAndView searchProducts(SearchParamsDto searchParamsDto);
}
