# springtainer-awss3mock

[![Maven Central](https://img.shields.io/maven-central/v/com.avides.springboot.springtainer/springtainer-awss3mock.svg?label=maven-central)](https://search.maven.org/artifact/com.avides.springboot.springtainer/springtainer-awss3mock)
[![Release](https://github.com/springtainer/springtainer-awss3mock/actions/workflows/release.yml/badge.svg)](https://github.com/springtainer/springtainer-awss3mock/actions/workflows/release.yml)
[![Nightly build](https://github.com/springtainer/springtainer-awss3mock/actions/workflows/nightly.yml/badge.svg)](https://github.com/springtainer/springtainer-awss3mock/actions/workflows/nightly.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=springtainer_springtainer-awss3mock&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=springtainer_springtainer-awss3mock)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=springtainer_springtainer-awss3mock&metric=coverage)](https://sonarcloud.io/summary/new_code?id=springtainer_springtainer-awss3mock)

### Dependency

```xml
<dependency>
  <groupId>com.avides.springboot.springtainer</groupId>
  <artifactId>springtainer-awss3mock</artifactId>
  <version>3.0.0-RC4</version>
  <scope>test</scope>
</dependency>
```

### Configuration

Properties consumed (in `bootstrap-it.properties`):

- `embedded.container.awss3mock.enabled` (default is `true`)
- `embedded.container.awss3mock.startup-timeout` (default is `30`)
- `embedded.container.awss3mock.docker-image` (default is `adobe/s3mock:5.1.0`)
- `embedded.container.awss3mock.endpoint-http-port` (default is `9090`)
- `embedded.container.awss3mock.endpoint-https-port` (default is `9191`)

Properties provided (in `application-it.properties`):

- `embedded.container.awss3mock.endpoint.http.url`
- `embedded.container.awss3mock.endpoint.https.url`

Example for minimal configuration in `application-it.properties`:

```
any-s3-endpoint.url=${embedded.container.awss3mock.endpoint.http.url}
```

A properly configured `S3Client` can be provided by the `AmazonS3Helper`.

## Spring's test-context cache is bounded automatically

`spring.test.context.cache.maxSize=1` ships as a classpath `spring.properties`
resource inside springtainer-common itself, so it's picked up automatically for every consumer - no configuration
needed on your side. This bounds Spring's test-context cache so a no-longer-current context (and, via its
`ContextClosedEvent` listener, its embedded container) gets evicted and cleanly closed as soon as a differently-configured
context needs the slot, instead of piling up unclosed until the whole JVM exits.

This works the same way whether tests are launched via Maven Surefire/Failsafe or directly from an IDE's own test
runner (e.g. Eclipse), since Spring resolves it from the classpath (`org.springframework.core.SpringProperties`) rather
than from a JVM system property.

## Logging

To reduce logging insert this into the logback-configuration:

```xml
<!-- Springtainer -->
<logger name="com.github.dockerjava" level="WARN" />
```

## Labels

The container exports multiple labels to analyze running springtainers:

- `SPRINGTAINER_SERVICE=awss3mock`
- `SPRINGTAINER_IMAGE=${embedded.container.awss3mock.docker-image}`
- `SPRINGTAINER_STARTED=$currentTimestamp`
