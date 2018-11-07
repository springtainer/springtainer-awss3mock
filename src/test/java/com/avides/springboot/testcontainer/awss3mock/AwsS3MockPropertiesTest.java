package com.avides.springboot.testcontainer.awss3mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AwsS3MockPropertiesTest
{
    @Test
    public void testDefaults()
    {
        var properties = new AwsS3MockProperties();
        assertTrue(properties.isEnabled());
        assertEquals(30, properties.getStartupTimeout());
        assertEquals("adobe/s3mock:2.1.0", properties.getDockerImage());
        assertEquals(9090, properties.getHttpPort());
        assertEquals(9191, properties.getHttpsPort());
        assertEquals("ERROR", properties.getLogLevel());
    }
}
