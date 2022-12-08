package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.ProductSearchSpecification;
import by.teachmeskills.eshop.services.ProductService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static by.teachmeskills.eshop.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.eshop.PagesPathEnum.SEARCH_PAGE;
import static by.teachmeskills.eshop.RequestParamsEnum.PRODUCT;
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
    public ModelAndView getCategoryProductsData(int id, String nameCategory, int pageNumber, int pageSize) {
        ModelMap modelMap = new ModelMap();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name", "description"));
        Page<Product> page = productRepository.findAllByCategoryId(id, paging);
        modelMap.addAttribute("nameCategory", nameCategory);
        modelMap.addAttribute("categoryId", id);
        modelMap.addAttribute("products", page.getContent());
        addAttributeToModelMap(page, pageNumber, pageSize, modelMap);
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }

    protected void addAttributeToModelMap(Page<? extends BaseEntity> page, int pageNumber, int pageSize, ModelMap modelMap) {
        modelMap.addAttribute("pageNumber", pageNumber);
        modelMap.addAttribute("pageSize", pageSize);
        modelMap.addAttribute("totalElements", page.getTotalElements());
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("isFirstPage", page.isFirst());
        modelMap.addAttribute("isLastPage", page.isLast());
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
    public ModelAndView searchProducts(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) {
        ModelMap modelMap = new ModelMap();
        ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParamsDto);
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name", "description"));
        Page<Product> page = productRepository.findAll(productSearchSpecification, paging);
        modelMap.addAttribute(SEARCH_PARAMS.getValue(), searchParamsDto);
        modelMap.addAttribute("foundProducts", page.getContent());
        addAttributeToModelMap(page, pageNumber, pageSize, modelMap);
        return new ModelAndView(SEARCH_PAGE.getPath(), modelMap);
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

    @Override
    public void writeProductsCategoryToCsv(int categoryId, HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("UTF8");
            response.addHeader("Content-Disposition", "attachment; filename=productsCategory.csv");
            List<Product> products = productRepository.findAllByCategoryId(categoryId);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(response.getWriter())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(products);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
            log.error(e.getMessage());
        }
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
