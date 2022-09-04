package by.teachmeskills.eshop;

public enum PagesPathEnum {
    START_PAGE("home"),
    SIGN_IN_PAGE("signin"),
    REGISTER_PAGE("register"),
    SEARCH_PAGE("search"),
    CATEGORY_PAGE("category"),
    CART_PAGE("cart"),
    PRODUCT_PAGE("product"),
    ORDER_PAGE("order"),
    UPLOAD_PAGE("upload"),
    UPLOAD2_PAGE("upload2"),
    ERROR_PAGE("error");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

