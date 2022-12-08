package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String searchKey;
    private String minPrice;
    private String maxPrice;
    private String categoryName;
}
