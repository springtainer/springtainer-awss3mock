# springtainer-awss3mock

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.avides.springboot.springtainer/springtainer-awss3mock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.avides.springboot.springtainer/springtainer-awss3mock)
[![Build](https://github.com/springtainer/springtainer-awss3mock/workflows/release/badge.svg)](https://github.com/springtainer/springtainer-awss3mock/actions)
[![Nightly build](https://github.com/springtainer/springtainer-awss3mock/workflows/nightly/badge.svg)](https://github.com/springtainer/springtainer-awss3mock/actions)
[![Coverage report](https://sonarcloud.io/api/project_badges/measure?project=springtainer_springtainer-awss3mock&metric=coverage)](https://sonarcloud.io/dashboard?id=springtainer_springtainer-awss3mock)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=springtainer_springtainer-awss3mock&metric=alert_status)](https://sonarcloud.io/dashboard?id=springtainer_springtainer-awss3mock)
[![Technical dept](https://sonarcloud.io/api/project_badges/measure?project=springtainer_springtainer-awss3mock&metric=sqale_index)](https://sonarcloud.io/dashboard?id=springtainer_springtainer-awss3mock)

### Dependency
```xml
<dependency>
	<groupId>com.avides.springboot.springtainer</groupId>
	<artifactId>springtainer-awss3mock</artifactId>
	<version>1.1.0</version>
	<scope>test</scope>
</dependency>
```

### Configuration
Properties consumed (in `bootstrap-it.properties`):
- `embedded.container.awss3mock.enabled` (default is `true`)
- `embedded.container.awss3mock.startup-timeout` (default is `30`)
- `embedded.container.awss3mock.docker-image` (default is `adobe/s3mock:2.1.21`)
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
<!-- Springtainer -->
<logger name="com.github.dockerjava.jaxrs" level="WARN" />
<logger name="com.github.dockerjava.core.command" level="WARN" />
<logger name="org.apache.http" level="WARN" />
```

## Labels
The container exports multiple labels to analyze running springtainers:
- `SPRINGTAINER_SERVICE=awss3mock`
- `SPRINGTAINER_IMAGE=${embedded.container.awss3mock.docker-image}`
- `SPRINGTAINER_STARTED=$currentTimestamp`

## Special Note
It's highly recommended to use BasicAWSCredentials with sample dates to avoid bad performance of the AWS-SDK.

Example:
`AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
	.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("acesskey", "secretkey")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3HttpEndpoint, Regions.EU_CENTRAL_1.getName()))
                .build();`

The `AmazonS3Helper` class can be used to build `AmazonS3` objects with sample credentials.
