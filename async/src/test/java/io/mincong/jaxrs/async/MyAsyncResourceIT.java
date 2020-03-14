package io.mincong.jaxrs.async;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.*;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyAsyncResourceIT {

  private static final URI uri = UriBuilder.fromUri("http://localhost/").port(8080).build();
  private static HttpServer server;
  private WebTarget target;

  @BeforeClass
  public static void setUpBeforeClass() {
    server = createServer();
  }

  @Before
  public void setUp() {
    target = ClientBuilder.newClient().target(uri);
  }

  @AfterClass
  public static void tearDownAfterClass() {
    server.shutdownNow();
  }

  private static HttpServer createServer() {
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
  public void longRunning1_sync() {
    Response r = target.path("async/longRunning1").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Welcome to async world!", r.readEntity(String.class));
  }

  @Test
  public void longRunning1_async() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    Future<String> future =
        target
            .path("async/longRunning1")
            .request()
            .async()
            .get(
                new InvocationCallback<String>() {
                  @Override
                  public void completed(String s) {
                    assertEquals("Welcome to async world!", s);
                    latch.countDown();
                  }

                  @Override
                  public void failed(Throwable throwable) {
                    fail(throwable.getMessage());
                  }
                });

    boolean countDown = latch.await(3, SECONDS);
    assertTrue(countDown);
    assertTrue(future.isDone());
    assertEquals("Welcome to async world!", future.get());
  }

  @Test
  public void longRunning2_sync() {
    Response r = target.path("async/longRunning2").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Welcome to async world, again!", r.readEntity(String.class));
  }

  @Test
  public void longRunning2_async() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    Future<String> future =
        target
            .path("async/longRunning2")
            .request()
            .async()
            .get(
                new InvocationCallback<String>() {
                  @Override
                  public void completed(String s) {
                    assertEquals("Welcome to async world, again!", s);
                    latch.countDown();
                  }

                  @Override
                  public void failed(Throwable throwable) {
                    fail(throwable.getMessage());
                  }
                });

    boolean countDown = latch.await(3, SECONDS);
    assertTrue(countDown);
    assertTrue(future.isDone());
    assertEquals("Welcome to async world, again!", future.get());
  }

  @Test
  public void longRunning3_sync() {
    Response r = target.path("async/longRunning3/fun").queryParam("key", "value").request().get();
    assertEquals(Status.OK.getStatusCode(), r.getStatus());
    assertEquals("Async world (id=fun, key=value)!", r.readEntity(String.class));
  }

  @Test
  public void longRunning3_async() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    Future<String> future =
        target
            .path("async/longRunning3/fun")
            .queryParam("key", "value")
            .request()
            .async()
            .get(
                new InvocationCallback<String>() {
                  @Override
                  public void completed(String s) {
                    assertEquals("Async world (id=fun, key=value)!", s);
                    latch.countDown();
                  }

                  @Override
                  public void failed(Throwable throwable) {
                    fail(throwable.getMessage());
                  }
                });

    boolean countDown = latch.await(3, SECONDS);
    assertTrue(countDown);
    assertTrue(future.isDone());
    assertEquals("Async world (id=fun, key=value)!", future.get());
  }
}
