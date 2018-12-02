package io.mincong.demo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class FailingExceptionMapper implements ExceptionMapper<FooException> {

  @Override
  public Response toResponse(FooException exception) {
    throw new IllegalStateException();
  }
}
