package io.mincong.jaxrs.async;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyAsyncResourceIT {

  private URI uri = UriBuilder.fromUri("http://localhost/").port(8080).build();
  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() {
    server = createServer();
    target = ClientBuilder.newClient().target(uri);
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  private HttpServer createServer() {
    Application application =
        new Application() {
          @Override
          public Set<Class<?>> getClasses() {
            return Collections.singleton(MyAsyncResource.class);
          }
        };
    ResourceConfig rc = ResourceConfig.forApplication(application);
    return GrizzlyHttpServerFactory.createHttpServer(uri, rc);
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

  @Test
  public void asyncResponse3() {
    Response r = target.path("async/longRunning3/fun").queryParam("key", "value").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Async world (id=fun, key=value)!", r.readEntity(String.class));
  }
}
