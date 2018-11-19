package io.mincong.shop.rest;

import io.mincong.shop.rest.dto.Product;
import io.mincong.shop.rest.dto.ProductCreated;
import java.util.regex.Pattern;

public class ProductResourceImpl implements ProductResource {

  private static final Pattern PATTERN_ID = Pattern.compile("\\p{Alnum}+");

  @Override
  public Product getProduct(String id) {
    if (id == null || !PATTERN_ID.matcher(id).matches()) {
      throw new ShopException(ShopError.PRODUCT_ID_INVALID);
    }
    return new Product(id, "foo");
  }

  @Override
  public ProductCreated createProduct(Product p) {
    String url = Main.BASE_URI.resolve("products").resolve(p.getId()).toString();
    return new ProductCreated(url);
  }

}
