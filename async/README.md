# Asynchronous Processing

Asynchronous processing is a technique that enables a better and more efficient
use of processing threads. On the client side, a thread that issues a request
may also be responsible for updating a UI component; if that thread is blocked
waiting for a response, the user’s perceived performance of the application
will suffer. Similarly, on the server side, a thread that is processing a
request should avoid blocking while waiting for an external event to complete
so that other requests arriving to the server during that period of time can be
attended. For more detail, see "Chapter 8 - Asynchronous Processing" of JAX-RS
2.1 specification (JSR-370).

## References

- Pavel Bucek and Santiago Pericas-Geertsen, "JSR 370 JAX-RS: JavaTM API for
  RESTful Web Services", _Oracle_, 2017.
