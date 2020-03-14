package io.mincong.jaxrs.async;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class MyApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<>();
    set.add(MyAsyncResource.class);
    return set;
  }

  @Override
  public Set<Object> getSingletons() {
    return Collections.emptySet();
  }
}
