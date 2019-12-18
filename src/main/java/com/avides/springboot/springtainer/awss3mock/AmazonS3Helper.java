package com.avides.springboot.springtainer.awss3mock;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.experimental.UtilityClass;

/**
 * This class provides builder methods to construct {@link AmazonS3} clients for use with a mocked S3.
 * <p>
 * A Client built by one of those methods explicitly uses sample credentials to avoid bad performance of the AWS-SDK.
 */
@UtilityClass
public class AmazonS3Helper
{
    /**
     * Returns a new built {@link AmazonS3} client with sample credentials, region "us-east-1" and path-style access.
     *
     * @param endpoint of the mocked S3
     * @param protocol used for communication with the mocked S3
     * @return a new built {@link AmazonS3} client
     */
    public static AmazonS3 buildAmazonS3(String endpoint, Protocol protocol)
    {
        return buildAmazonS3(endpoint, protocol, "us-east-1");
    }

    /**
     * Returns a new built {@link AmazonS3} client with sample credentials and path-style access.
     *
     * @param endpoint of the mocked S3
     * @param protocol used for communication with the mocked S3
     * @param region used by the returned {@link AmazonS3}
     * @return a new built {@link AmazonS3} client
     */
    public static AmazonS3 buildAmazonS3(String endpoint, Protocol protocol, String region)
    {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("acesskey", "secretkey")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withPathStyleAccessEnabled(Boolean.TRUE)
                .withClientConfiguration(new ClientConfiguration().withProtocol(protocol))
                .build();
    }
}
