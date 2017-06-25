# Demo app for the "How to store a request body in Redis" post

[![Build Status](https://travis-ci.com/xsolve-pl/redis-with-spring-mvc.svg?token=dd2vNedz3QZLxhqGs12s&branch=master)](https://travis-ci.com/xsolve-pl/redis-with-spring-mvc)

## Blog post

The relevant blog post can be found [on our XSolve blog](https://xsolve.software/blog/redis-with-spring-mvc/)

## How to

Run the demo with 

```bash
docker-compose up
```

Then, once the application starts, execute a test request:

```bash
curl -H "Content-Type: application/json" \\
     -H 'X-MessageSystems-Batch-ID: 123-456' \\
     -d '{"data":"test data"}' localhost:8080/
```

Next, verify that the request payload has been saved in your redis instance, available under `localhost:6379`, under the key `123-456`. For this purpose,
we recommend using the [Redis Desktop Manager](https://github.com/uglide/RedisDesktopManager/releases/tag/0.8.3).

### Cleanup

Execute
```bash
docker-compose down
```

## Tests

Don't forget to check out the `StoreRequestInRedisTest`, which can be run with `./gradlew test`.
