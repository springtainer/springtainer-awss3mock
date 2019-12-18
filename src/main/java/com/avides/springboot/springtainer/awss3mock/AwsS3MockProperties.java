package com.avides.springboot.springtainer.awss3mock;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.avides.springboot.springtainer.common.container.AbstractEmbeddedContainerProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties("embedded.container.awss3mock")
@Getter
@Setter
@ToString(callSuper = true)
public class AwsS3MockProperties extends AbstractEmbeddedContainerProperties
{
    public static final String BEAN_NAME = "embeddedAwsS3MockContainer";

    private int endpointHttpPort = 9090;

    private int endpointHttpsPort = 9191;

    public AwsS3MockProperties()
    {
        setDockerImage("adobe/s3mock:2.1.16");
    }
}
