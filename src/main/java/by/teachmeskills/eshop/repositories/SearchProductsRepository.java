package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;
import java.util.List;
import java.util.Map;

public interface SearchProductsRepository {
    List<Product> findProductsListByParams(Map<String, String> searchParams);
}
