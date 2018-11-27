package io.mincong.demo;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("params")
@Produces(MediaType.TEXT_PLAIN)
public class ParamResource {

  @GET
  @Path("queryParam")
  public Response getParams(
      @QueryParam("s") @DefaultValue("") String myStr,
      @QueryParam("i") @DefaultValue("-1") int myInt) {
    String s = "s=" + myStr + ", i=" + myInt;
    return Response.ok(s).build();
  }

  @GET
  @Path("pathParam/{p}")
  public Response getParams(@PathParam("p") String v) {
    return Response.ok(v).build();
  }

  @GET
  @Path("headerParam")
  public Response getHeaderParam(@HeaderParam("p") String v) {
    return Response.ok(v).build();
  }

  @GET
  @Path("cookieParam")
  public Response getCookieParam(@CookieParam("p") String v) {
    return Response.ok(v).build();
  }

  @POST
  @Path("formParam")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response postFormParam(@FormParam("p") String v) {
    return Response.ok(v).build();
  }

  @GET
  @Path("matrixParam")
  public Response getMatrixParam(
      @MatrixParam("height") int height, @MatrixParam("width") int width) {
    return Response.ok("height=" + height + ", width=" + width).build();
  }

  @POST
  @Path("beanParam")
  public Response postBeanParam(@BeanParam Image image) {
    String s = "height=" + image.getHeight();
    s += ", width=" + image.getWidth();
    return Response.ok(s).build();
  }
}
