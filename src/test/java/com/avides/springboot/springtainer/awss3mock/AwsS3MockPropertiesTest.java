package com.avides.springboot.springtainer.awss3mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AwsS3MockPropertiesTest
{
    @Test
    public void testDefaults()
    {
        AwsS3MockProperties properties = new AwsS3MockProperties();
        assertTrue(properties.isEnabled());
        assertEquals(30, properties.getStartupTimeout());
        assertEquals("adobe/s3mock:2.4.7", properties.getDockerImage());
        assertEquals(9090, properties.getEndpointHttpPort());
        assertEquals(9191, properties.getEndpointHttpsPort());
    }
}
