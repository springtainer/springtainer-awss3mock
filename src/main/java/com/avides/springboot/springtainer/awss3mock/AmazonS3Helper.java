package com.avides.springboot.springtainer.awss3mock;

import java.net.URI;

import lombok.experimental.UtilityClass;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * This class provides builder methods to construct {@link S3Client}s for use with a mocked S3.
 * <p>
 * A client built by one of those methods explicitly uses sample credentials to avoid bad performance of the AWS-SDK.
 */
@UtilityClass
public class AmazonS3Helper
{
    /**
     * Returns a new built {@link S3Client} with sample credentials, region "us-east-1" and path-style access.
     *
     * @param endpoint of the mocked S3 (host:port, without scheme)
     * @param protocol used for communication with the mocked S3
     * @return a new built {@link S3Client}
     */
    public static S3Client buildS3Client(String endpoint, Protocol protocol)
    {
        return buildS3Client(endpoint, protocol, "us-east-1");
    }

    /**
     * Returns a new built {@link S3Client} with sample credentials and path-style access.
     *
     * @param endpoint of the mocked S3 (host:port, without scheme)
     * @param protocol used for communication with the mocked S3
     * @param region used by the returned {@link S3Client}
     * @return a new built {@link S3Client}
     */
    public static S3Client buildS3Client(String endpoint, Protocol protocol, String region)
    {
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("acesskey", "secretkey")))
                .endpointOverride(URI.create((protocol == Protocol.HTTPS ? "https://" : "http://") + endpoint))
                .region(Region.of(region))
                .forcePathStyle(true)
                .build();
    }
}
