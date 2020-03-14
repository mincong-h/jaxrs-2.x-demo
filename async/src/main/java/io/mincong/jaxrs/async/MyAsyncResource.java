package io.mincong.jaxrs.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("async")
public class MyAsyncResource {

  @GET
  @Path("longRunning1")
  public void longRunningOp1(@Suspended AsyncResponse response) {
    Executors.newCachedThreadPool()
        .submit(
            () -> {
              Thread.sleep(100);
              response.resume("Welcome to async world!");
              return null;
            });
  }

  @GET
  @Path("longRunning2")
  public CompletionStage<String> longRunningOp2() {
    CompletableFuture<String> future = new CompletableFuture<>();
    Executors.newCachedThreadPool()
        .submit(
            () -> {
              Thread.sleep(100);
              future.complete("Welcome to async world, again!");
              return null;
            });
    return future;
  }
}
