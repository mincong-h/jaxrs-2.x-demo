package io.mincong.shop.rest;

import io.mincong.shop.rest.dto.Product;
import io.mincong.shop.rest.dto.ProductCreated;
import io.mincong.shop.rest.dto.ShopExceptionData;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Product resource integration test.
 *
 * @author Mincong Huang
 */
public class ProductResourceIT {

  private HttpServer server;

  private WebTarget target;

  @Before
  public void setUp() {
    server = Main.startServer();
    target =
        ClientBuilder.newBuilder()
            .register(ShopApplication.newJacksonJsonProvider())
            .register((ClientResponseFilter) (requestCtx, responseCtx) -> {
              if (responseCtx instanceof ClientResponse) {
                ClientResponse resp = (ClientResponse) responseCtx;
                if (resp.getStatus() >= 400) {
                  ShopExceptionData data = resp.readEntity(ShopExceptionData.class);
                  throw new ShopException(resp.getStatus(), data);
                }
              }
            })
            .build()
            .target(Main.BASE_URI.resolve("products"));
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void getProduct_asString() {
    String s = target.path("123").request(APPLICATION_JSON).get(String.class);
    assertThat(s).isEqualTo("{\"id\":\"123\",\"name\":\"foo\"}");
  }

  @Test
  public void getProduct_invalidId() {
    try {
      target.path("123!").request(APPLICATION_JSON).get(Product.class);
      fail("GET should raise an exception");
    } catch (ProcessingException pe) {
      // Perhaps there's a better solution
      ShopException e = (ShopException) pe.getCause();
      assertThat(e.getData().getErrorCode()).isEqualTo(ShopError.PRODUCT_ID_INVALID.code);
      assertThat(e.getData().getErrorMessage()).isEqualTo(ShopError.PRODUCT_ID_INVALID.message);
    }
  }

  @Test
  public void getProduct() {
    Product p = target.path("123").request(APPLICATION_JSON).get(Product.class);
    assertThat(p).isEqualTo(new Product("123", "foo"));
  }

  @Test
  public void createProduct() {
    Product p = new Product("123", "foo");
    ProductCreated c =
        target
            .request(APPLICATION_JSON)
            .post(Entity.entity(p, APPLICATION_JSON), ProductCreated.class);
    assertThat(c.getUrl()).endsWith(p.getId());
    assertThat(c.getCreated()).isNotNull();
  }
}
