package com.avides.springboot.testcontainer.awss3mock;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AmazonS3Helper
{
    public static AmazonS3 buildAmazonS3(String endpoint, Protocol protocol)
    {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "us-east-1"))
                .withPathStyleAccessEnabled(Boolean.TRUE)
                .withClientConfiguration(new ClientConfiguration().withProtocol(protocol))
                .build();
    }
}
