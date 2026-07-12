package com.avides.springboot.springtainer.awss3mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

public class EmbeddedAwsS3MockContainerAutoConfigurationIT extends AbstractIT
{
    @Test
    public void testGeneratedProperties() throws Exception
    {
        assertThat(environment.getProperty("embedded.container.awss3mock.endpoint.http.url")).isNotEmpty();
        assertThat(environment.getProperty("embedded.container.awss3mock.endpoint.https.url")).isNotEmpty();

        System.out.println();
        System.out.println("Resolved properties:");
        System.out.println("Http endpoint url : " + environment.getProperty("embedded.container.awss3mock.endpoint.http.url"));
        System.out.println("Https endpoint url: " + environment.getProperty("embedded.container.awss3mock.endpoint.https.url"));
    }

    @Test
    public void testAmazonS3() throws Exception
    {
        s3Client.createBucket(CreateBucketRequest.builder().bucket("testbucket").build());
        assertEquals(0, s3Client.listObjectsV2(ListObjectsV2Request.builder().bucket("testbucket").prefix("test/1").build()).contents().size());
    }

    @Configuration
    @EnableAutoConfiguration
    static class TestConfiguration
    {
        // nothing
    }
}
