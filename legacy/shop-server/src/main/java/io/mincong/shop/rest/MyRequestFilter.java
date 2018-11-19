package io.mincong.shop.rest;

import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class MyRequestFilter implements ContainerRequestFilter {

  private static final Logger logger = Logger.getLogger(MyRequestFilter.class.getName());

  @Override
  public void filter(ContainerRequestContext requestContext) {
    logger.info("filter");
  }
}
