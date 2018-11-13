package io.mincong.shop.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class PingResourceIT {

  private HttpServer server;

  private WebTarget target;

  @Before
  public void setUp() {
    server = Main.startServer();
    target =
        ClientBuilder.newBuilder()
            .register(ShopApplication.newJacksonJsonProvider())
            .build()
            .target(Main.BASE_URI.resolve("ping"));
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void ping() {
    Response response = target.request().head();
    assertThat(response.getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());
  }
}
