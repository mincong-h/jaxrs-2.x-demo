package io.mincong.demo;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class MyApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<>();
    set.add(DemoResource.class);
    return set;
  }

  @Override
  public Set<Object> getSingletons() {
    Set<Object> set = new HashSet<>();
    set.add(new MapperA());
    set.add(new MapperA1());
    set.add(new FailingExceptionMapper());
    return set;
  }
}
