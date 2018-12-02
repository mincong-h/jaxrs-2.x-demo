package io.mincong.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class DemoResource {

  @GET
  @Path("a")
  public Response newExceptionA() {
    throw new ExceptionA("Exception A");
  }

  @GET
  @Path("a1")
  public Response newExceptionA1() {
    throw new ExceptionA1("Exception A1");
  }

  @GET
  @Path("a2")
  public Response newExceptionA2() {
    throw new ExceptionA2("Exception A2");
  }

  @GET
  @Path("failing")
  public Response newFooException() {
    throw new FooException();
  }

  @GET
  @Path("unmatched")
  public Response newIllegalStateException() {
    throw new IllegalStateException("Unexpected Exception");
  }
}
