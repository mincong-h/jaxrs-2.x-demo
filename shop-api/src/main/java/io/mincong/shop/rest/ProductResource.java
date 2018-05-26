package io.mincong.shop.rest;

import io.mincong.shop.rest.dto.Product;
import io.mincong.shop.rest.dto.ProductCreated;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("products")
public interface ProductResource {

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  Product getProduct(@PathParam("id") String id);

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ProductCreated createProduct(Product p);
}
