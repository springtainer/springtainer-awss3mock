package com.avides.springboot.springtainer.awss3mock;

/**
 * Protocol to use when communicating with the mocked S3, used to build the endpoint URI passed to {@link AmazonS3Helper}.
 */
public enum Protocol
{
    HTTP,
    HTTPS
}
