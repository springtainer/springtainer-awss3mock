# springboot-testcontainer-awss3mock

[![Maven Central](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/avides/springboot/testcontainer/springboot-testcontainer-awss3mock/maven-metadata.xml.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.avides.springboot.testcontainer%22%20AND%20a%3A%22springboot-testcontainer-awss3mock%22)
[![Build](https://github.com/springboot-testcontainer/springboot-testcontainer-awss3mock/workflows/release/badge.svg)](https://github.com/springboot-testcontainer/springboot-testcontainer-awss3mock/actions)
[![Nightly build](https://github.com/springboot-testcontainer/springboot-testcontainer-awss3mock/workflows/nightly/badge.svg)](https://github.com/springboot-testcontainer/springboot-testcontainer-awss3mock/actions)
[![Coverage report](https://sonarcloud.io/api/project_badges/measure?project=springboot-testcontainer_springboot-testcontainer-awss3mock&metric=coverage)](https://sonarcloud.io/dashboard?id=springboot-testcontainer_springboot-testcontainer-awss3mock)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=springboot-testcontainer_springboot-testcontainer-awss3mock&metric=alert_status)](https://sonarcloud.io/dashboard?id=springboot-testcontainer_springboot-testcontainer-awss3mock)
[![Technical dept](https://sonarcloud.io/api/project_badges/measure?project=springboot-testcontainer_springboot-testcontainer-awss3mock&metric=sqale_index)](https://sonarcloud.io/dashboard?id=springboot-testcontainer_springboot-testcontainer-awss3mock)

### Dependency
```xml
<dependency>
	<groupId>com.avides.springboot.testcontainer</groupId>
	<artifactId>springboot-testcontainer-awss3mock</artifactId>
	<version>1.0.0-RC3</version>
	<scope>test</scope>
</dependency>
```

### Configuration
Properties consumed (in `bootstrap-it.properties`):
- `embedded.container.awss3mock.enabled` (default is `true`)
- `embedded.container.awss3mock.startup-timeout` (default is `30`)
- `embedded.container.awss3mock.docker-image` (default is `adobe/s3mock:2.1.16`)
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

## Special Note
It's highly recommended to use BasicAWSCredentials with sample dates to avoid bad performance of the AWS-SDK.

Example:
`AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
	.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("acesskey", "secretkey")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3HttpEndpoint, Regions.EU_CENTRAL_1.getName()))
                .build();`

The `AmazonS3Helper` class can be used to build `AmazonS3` objects with sample credentials.
