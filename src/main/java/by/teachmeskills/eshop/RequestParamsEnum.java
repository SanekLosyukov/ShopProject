package by.teachmeskills.eshop;

public enum RequestParamsEnum {
    LOGIN("username"),
    PASSWORD("password"),
    COMMAND("command"),
    SHOPPING_CART("cart"),
    ORDER("order"),
    USER("user"),
    ORDERPRODUCTS("orderProducts"),
    SEARCHED_PRODUCTS("searchedProducts"),
    SEARCH_PARAMS("searchParams"),
    PRODUCT("product"),
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    POPULAR_CATEGORIES_LIST_REQ_PARAM("categories"),
    CATEGORY_PARAM("category"),
    PRODUCT_ID("product_id"),
    ERROR_PARAM("error");

    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

