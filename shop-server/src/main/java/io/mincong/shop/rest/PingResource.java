package io.mincong.shop.rest;

import javax.ws.rs.HEAD;
import javax.ws.rs.Path;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("ping")
public class PingResource {

  @HEAD
  public void ping() {
    // do nothing
  }
}
