package io.mincong.shop.rest;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("books")
public class BookResource {

  static final AtomicInteger id = new AtomicInteger(1);
  static final Map<Integer, Book> books =
      new HashMap<>(Collections.singletonMap(id.get(), new Book(id.get(), "Awesome")));

  /**
   * The GET method requests a representation of the specified resource. Requests using GET should
   * only retrieve data.
   *
   * @param id target book id
   * @return HTTP response
   */
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBook(@PathParam("id") int id) {
    if (books.containsKey(id)) {
      return Response.ok(books.get(id).toJson()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * The HEAD method asks for a response identical to that of a GET request, but without the
   * response body.
   *
   * @param id target book id
   * @return HTTP response without response body
   */
  @HEAD
  @Path("{id}")
  public Response getBookStatus(@PathParam("id") int id) {
    if (books.containsKey(id)) {
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * The POST method is used to submit an entity to the specified resource, often causing a change
   * in state or side effects on the server.
   */
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createBook(@FormParam("name") String name) {
    Book book = new Book(id.incrementAndGet(), name);
    books.put(book.id, book);
    URI uri = UriBuilder.fromUri(Main.BASE_URI).path("books").path("" + book.id).build();
    return Response.created(uri).build();
  }

  /**
   * The PUT method replaces all current representations of the target resource with the request
   * payload.
   *
   * @param id target book id
   * @param name target book name
   * @return HTTP response
   */
  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateOrCreateBook(@PathParam("id") int id, @FormParam("name") String name) {
    Book book = new Book(id, name);
    books.put(book.id, book);
    return Response.ok().entity(book.toJson()).build();
  }

  /**
   * The DELETE method deletes the specified resource.
   *
   * @param id target book id
   * @return HTTP response
   */
  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteBook(@PathParam("id") int id) {
    if (books.containsKey(id)) {
      Book book = books.remove(id);
      return Response.ok().entity(book.toJson()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  public static class Book {
    private int id;
    private String name;

    Book(int id, String name) {
      this.id = id;
      this.name = name;
    }

    private String toJson() {
      return String.format("{\"id\":%d,\"name\":\"%s\"}", id, name);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Book)) {
        return false;
      }
      Book book = (Book) o;
      return Objects.equals(id, book.id) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, name);
    }
  }
}
