package io.mincong.shop.rest;

import io.mincong.shop.rest.dto.Product;
import io.mincong.shop.rest.dto.ProductCreated;

public class ProductResourceImpl implements ProductResource {

  @Override
  public Product getProduct(String id) {
    return new Product(id, "foo");
  }

  @Override
  public ProductCreated createProduct(Product p) {
    String url = Main.BASE_URI.resolve("products").resolve(p.getId()).toString();
    return new ProductCreated(url);
  }

}
