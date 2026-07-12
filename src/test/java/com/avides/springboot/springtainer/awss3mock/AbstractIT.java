package com.avides.springboot.springtainer.awss3mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.avides.springboot.springtainer.common.util.DockerClients;
import com.github.dockerjava.api.DockerClient;

import software.amazon.awssdk.services.s3.S3Client;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
public abstract class AbstractIT
{
    protected DockerClient dockerClient = DockerClients.build();

    @Autowired
    protected ConfigurableEnvironment environment;

    protected S3Client s3Client;

    @BeforeEach
    public void init()
    {
        s3Client = AmazonS3Helper.buildS3Client(environment.getProperty("embedded.container.awss3mock.endpoint.http.url"), Protocol.HTTP);
    }
}
