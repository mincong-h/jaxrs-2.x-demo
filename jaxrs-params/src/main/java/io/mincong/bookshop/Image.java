package io.mincong.bookshop;

import javax.ws.rs.FormParam;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class Image {

  @FormParam("height")
  private int height;

  @FormParam("width")
  private int width;

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
