package io.mincong.jaxrs.async;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyAsyncResourceIT {

  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() {
    server = Main.startServer();
    target = ClientBuilder.newClient().target(Main.BASE_URI);
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void asyncResponse1() {
    Response r = target.path("async/longRunning1").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Welcome to async world!", r.readEntity(String.class));
  }

  @Test
  public void asyncResponse2() {
    Response r = target.path("async/longRunning2").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Welcome to async world, again!", r.readEntity(String.class));
  }
}
