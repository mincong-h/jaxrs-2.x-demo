package io.mincong.demo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class DemoResourceIT {

  private HttpServer server;

  private WebTarget target;

  @Before
  public void setUp() {
    server = Main.startServer();
    target = ClientBuilder.newBuilder().build().target(Main.BASE_URI);
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void exceptionMapping() {
    Response r = target.path("a").request().get();
    assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
    assertEquals("Mapper A: Exception A", r.readEntity(String.class));

    r = target.path("a1").request().get();
    assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
    assertEquals("Mapper A1: Exception A1", r.readEntity(String.class));

    r = target.path("a2").request().get();
    assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
    assertEquals("Mapper A: Exception A2", r.readEntity(String.class));

    r = target.path("failing").request().get();
    assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
    assertEquals("", r.readEntity(String.class));

    r = target.path("unmatched").request().get();
    assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
    assertTrue(r.readEntity(String.class).startsWith("<html><head><title>"));
  }

}
