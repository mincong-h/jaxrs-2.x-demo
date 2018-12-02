package io.mincong.demo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MapperA implements ExceptionMapper<ExceptionA> {

  @Override
  public Response toResponse(ExceptionA ex) {
    return Response.status(400).entity("Mapper A: " + ex.getMessage()).build();
  }
}
