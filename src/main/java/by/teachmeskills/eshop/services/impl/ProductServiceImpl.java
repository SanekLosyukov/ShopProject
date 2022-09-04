package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.ProductSearchSpecification;
import by.teachmeskills.eshop.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static by.teachmeskills.eshop.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.SEARCH_PAGE;
import static by.teachmeskills.eshop.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.eshop.RequestParamsEnum.SEARCHED_PRODUCTS;
import static by.teachmeskills.eshop.RequestParamsEnum.SEARCH_PARAMS;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ProductConverter productConverter;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productConverter = productConverter;
    }

    @Override
    public Product create(Product entity) {
        return null;
    }

    @Override
    public List<Product> read() {
        return null;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public List<Product> getAllForCategory(int categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Product> getAllForCategoryPaged(int categoryId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Page<Product> products = productRepository.findAllByCategoryId(categoryId, paging);
        return products.getContent();
    }

    @Override
    public List<Product> getAllByCategoryAndNamePaged(int categoryId, String name, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findAllByCategoryIdAndNameContaining(categoryId, name, paging);
        return products.getContent();
    }

    @Override
    public ModelAndView getProductData(int id) {
        ModelMap model = new ModelMap();
        productRepository.findById(id).ifPresent(p -> {
            model.addAttribute(PRODUCT.getValue(), p);
        });
        return new ModelAndView(PRODUCT_PAGE.getPath(), model);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            product = productRepository.save(product);
            return productConverter.toDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ModelAndView searchProducts(SearchParamsDto searchParamsDto) {
        ModelMap modelParams = new ModelMap();
        ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParamsDto);
        List<ProductDto> products = productRepository.findAll(productSearchSpecification).stream().map(productConverter::toDto).toList();
        modelParams.addAttribute(SEARCH_PARAMS.getValue(), searchParamsDto);
        modelParams.addAttribute(SEARCHED_PRODUCTS.getValue(), products);
        return new ModelAndView(SEARCH_PAGE.getPath(), modelParams);
    }

    @Override
    public List<ProductDto> saveProductsFromFile(MultipartFile file) {
        List<ProductDto> csvProducts = parseCsv(file);
        List<Product> products = Optional.ofNullable(csvProducts)
                .map(list -> list.stream()
                        .map(productConverter::fromDto)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(products).isPresent()) {
            productRepository.saveAll(products);
            return csvProducts;
        }
        return Collections.emptyList();
    }

    private List<ProductDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();

                return csvToBean.parse();
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {}", ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }
}
