package io.mincong.shop.rest;

import io.mincong.shop.rest.BookResource.Book;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
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
public class BookResourceIT {

  private HttpServer server;

  private WebTarget target;

  @Before
  public void setUp() {
    AtomicInteger id = BookResource.id;
    id.set(1);
    BookResource.books.clear();
    BookResource.books.put(id.get(), new Book(id.get(), "Awesome"));

    server = Main.startServer();
    target = ClientBuilder.newBuilder().build().target(Main.BASE_URI.resolve("books"));
  }

  @After
  public void tearDown() {
    server.shutdownNow();
    BookResource.books.clear();
  }

  @Test
  public void testGet() {
    Response response = target.path("1").request().get();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("{\"id\":1,\"name\":\"Awesome\"}", response.readEntity(String.class));

    response = target.path("2").request().get();
    assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    assertEquals("", response.readEntity(String.class));
  }

  @Test
  public void testHead() {
    Response response = target.path("1").request().head();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("", response.readEntity(String.class));

    response = target.path("2").request().get();
    assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    assertEquals("", response.readEntity(String.class));
  }

  @Test
  public void testPost() {
    Form form = new Form();
    form.param("name", "Cool");
    Response response = target.request().post(Entity.form(form));
    assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    assertEquals(URI.create("http://localhost:8080/books/2"), response.getLocation());
    assertEquals("", response.readEntity(String.class));
  }

  @Test
  public void testPut() {
    // Given an existing book
    Response response = target.path("1").request().get();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("{\"id\":1,\"name\":\"Awesome\"}", response.readEntity(String.class));

    // When updating it using a different name
    Form form = new Form();
    form.param("name", "Cool");
    response = target.path("1").request().put(Entity.form(form));

    // Then the book is updated
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("{\"id\":1,\"name\":\"Cool\"}", response.readEntity(String.class));
  }

  @Test
  public void testDelete() {
    // Given an existing book
    Response response = target.path("1").request().get();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("{\"id\":1,\"name\":\"Awesome\"}", response.readEntity(String.class));

    // When deleting it
    response = target.path("1").request().delete();

    // Then request is successful: the book is deleted
    assertEquals(Status.OK.getStatusCode(), response.getStatus());
    assertEquals("{\"id\":1,\"name\":\"Awesome\"}", response.readEntity(String.class));

    // When deleting it again
    response = target.path("1").request().delete();

    // Then request is failed: book is not found
    assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    assertEquals("", response.readEntity(String.class));
  }
}
