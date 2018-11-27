package io.mincong.demo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
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
public class ParamResourceIT {

  private HttpServer server;

  private WebTarget target;

  @Before
  public void setUp() {
    server = Main.startServer();
    target = ClientBuilder.newBuilder().build().target(Main.BASE_URI.resolve("params"));
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void queryParam() {
    Response r = target.path("queryParam").queryParam("s", "hi").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("s=hi, i=-1", r.readEntity(String.class));

    r = target.path("queryParam").queryParam("i", "4").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("s=, i=4", r.readEntity(String.class));

    r = target.path("queryParam").queryParam("s", "hi").queryParam("i", "4").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("s=hi, i=4", r.readEntity(String.class));
  }

  @Test
  public void pathParam() {
    Response r = target.path("pathParam").path("foo").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("foo", r.readEntity(String.class));
  }

  @Test
  public void headerParam() {
    Response r = target.path("headerParam").request().header("p", "foo").get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("foo", r.readEntity(String.class));
  }

  @Test
  public void cookieParam() {
    Response r = target.path("cookieParam").request().cookie("p", "foo").get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("foo", r.readEntity(String.class));
  }

  @Test
  public void formParam() {
    Form form = new Form();
    form.param("p", "foo");
    Response r = target.path("formParam").request().post(Entity.form(form));
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("foo", r.readEntity(String.class));
  }

  @Test
  public void matrixParam() {
    Response r =
        target.path("matrixParam").matrixParam("height", 1).matrixParam("width", 2).request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("height=1, width=2", r.readEntity(String.class));
  }

  @Test
  public void beanParam() {
    Form form = new Form();
    form.param("height", "1");
    form.param("width", "2");
    Response r = target.path("beanParam").request().post(Entity.form(form));
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("height=1, width=2", r.readEntity(String.class));
  }
}
