package io.mincong.demo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MapperA1 implements ExceptionMapper<ExceptionA1> {

  @Override
  public Response toResponse(ExceptionA1 ex) {
    return Response.status(400).entity("Mapper A1: " + ex.getMessage()).build();
  }
}
