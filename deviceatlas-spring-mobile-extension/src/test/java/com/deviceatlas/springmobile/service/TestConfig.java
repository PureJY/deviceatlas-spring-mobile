package com.deviceatlas.springmobile.service;

import com.deviceatlas.cloud.deviceidentification.client.Client;
import com.deviceatlas.cloud.deviceidentification.client.ClientException;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheException;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheProvider;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.SimpleCacheProvider;
import com.deviceatlas.springmobile.service.DeviceAtlasDeviceResolver;
import com.deviceatlas.springmobile.Config;
import com.deviceatlas.springmobile.DeviceAtlasPreferences;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.DeviceResolver;

@Configuration
public class TestConfig {
    @Bean(destroyMethod="shutdown")
    public Client client() throws ClientException {
        Client client = null;
        String licenceKey = System.getProperty("licenceKey");
        if (licenceKey == null || "".equals(licenceKey)) {
            throw new ClientException("A valid licence key must be set");
        }
        
        try {
            client = Client.getInstance(new SimpleCacheProvider());
            client.setLicenceKey(licenceKey);
        } catch (CacheException ex) {
            ex.printStackTrace();
        }

        return client;
    }

    @Bean
    public DeviceResolver deviceAtlasDeviceResolver() {
        return new DeviceAtlasDeviceResolver();
    }
}
