package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;
    public ProductConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .mainImage(product.getMainImage())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product fromDto(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .mainImage(productDto.getMainImage())
                .category(categoryRepository.findById(productDto.getCategoryId()).orElse(null))
                .build();
    }
}
