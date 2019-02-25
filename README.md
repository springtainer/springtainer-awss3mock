springboot-testcontainer-awss3mock
==================================

[![Maven Central](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/avides/springboot/testcontainer/springboot-testcontainer-awss3mock/maven-metadata.xml.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.avides.springboot.testcontainer%22%20AND%20a%3A%22springboot-testcontainer-awss3mock%22)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3ef2b23118074ae7bbe52a3bd53defad)](https://www.codacy.com/app/avides-builds/springboot-testcontainer-awss3mock)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/3ef2b23118074ae7bbe52a3bd53defad)](https://www.codacy.com/app/avides-builds/springboot-testcontainer-awss3mock)
[![Build Status](https://travis-ci.org/springboot-testcontainer/springboot-testcontainer-awss3mock.svg?branch=master)](https://travis-ci.org/springboot-testcontainer/springboot-testcontainer-awss3mock)

### Dependency
```xml
<dependency>
	<groupId>com.avides.springboot.testcontainer</groupId>
	<artifactId>springboot-testcontainer-awss3mock</artifactId>
	<version>0.1.0-RC4</version>
	<scope>test</scope>
</dependency>
```

### Configuration
Properties consumed (in `bootstrap-it.properties`):
- `embedded.container.awss3mock.enabled` (default is `true`)
- `embedded.container.awss3mock.startup-timeout` (default is `30`)
- `embedded.container.awss3mock.docker-image` (default is `adobe/s3mock:2.1.0`)
- `embedded.container.awss3mock.endpoint-http-port` (default is `9090`)
- `embedded.container.awss3mock.endpoint-https-port` (default is `9191`)

Properties provided (in `application-it.properties`):
- `embedded.container.awss3mock.endpoint.http.url`
- `embedded.container.awss3mock.endpoint.https.url`

Example for minimal configuration in `application-it.properties`:
```
any-s3-endpoint.url=${embedded.container.awss3mock.endpoint.http.url}
```

A properly configured AmazonS3 Object can be provided by the AmazonS3Helper.

## Logging
To reduce logging insert this into the logback-configuration:
```xml
<!-- Testcontainers -->
<logger name="com.github.dockerjava.jaxrs" level="WARN" />
<logger name="com.github.dockerjava.core.command" level="WARN" />
<logger name="org.apache.http" level="WARN" />
```

## Labels
The container exports multiple labels to analyze running testcontainers:
- `TESTCONTAINER_SERVICE=awss3mock`
- `TESTCONTAINER_IMAGE=${embedded.container.awss3mock.docker-image}`
- `TESTCONTAINER_STARTED=$currentTimestamp`