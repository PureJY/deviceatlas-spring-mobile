/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Afilias Technologies Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.deviceatlas.springmobile;

import com.deviceatlas.cloud.deviceidentification.cacheprovider.EhCacheCacheProvider;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheException;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheProvider;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.FileCacheProvider;
import com.deviceatlas.cloud.deviceidentification.client.Client;
import com.deviceatlas.cloud.deviceidentification.client.ClientException;
import com.deviceatlas.cloud.deviceidentification.endpoint.EndPoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class Config {
    @Autowired
    private DeviceAtlasPreferences daPreferences;
    @Autowired
    private CacheProvider cacheProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    /**
     * Returns the Client object to identify the device.
     * 
     * Licence key and cache provider are mandatory fields to be able to make 
     * the device identification.
     * 
     * @throws ClientException when there is an issue with the given licence key
     * @throws CacheException when there is an issue with the given cache provider
     * @return Client
     */
    @Bean
    public Client deviceIdentificator() throws ClientException, CacheException {

        Client client = Client.getInstance(cacheProvider);

        // Mandatory setting
        client.setLicenceKey(daPreferences.getLicenceKey());

        // Optional settings
        client.setServerRankingLifetime(daPreferences.getServerRankingLifetime());
        client.setAutoServerRankingNumRequests(daPreferences.getAutoServerRankingNumRequests());
        client.setAutoServerRankingMaxFailures(daPreferences.getAutoServerRankingMaxFailures());
        client.setAutoServerRanking(daPreferences.getAutoServerRanking());
        client.setUseCache(daPreferences.getUseCache());
        client.setUseClientCookie(daPreferences.getUseClientCookie());
        client.setCloudServiceTimeout(daPreferences.getCloudServiceTimeout());
        if (daPreferences.getEndPointUrls() != null) {
            List<EndPoint> endPoints = getEndPoints(daPreferences.getEndPointUrls());
            client.setEndPoints(endPoints.toArray(new EndPoint[endPoints.size()]));
        }

        return client;
    }

    // TODO: Remove this method and get the endpoint fields from an object
    // directly from the yaml file.
    private List<EndPoint> getEndPoints(List<String> endPointUrls) {
        List<EndPoint> endPoints = new ArrayList<EndPoint>();
        for (String endPointUrl: endPointUrls) {
            String[] endPointFields = endPointUrl.split(":");
            endPoints.add(new EndPoint(endPointFields[0] + ":" + endPointFields[1], endPointFields[2]));
        }
        return endPoints;
    }

    @Profile("prod")
    @Bean
    public CacheProvider ehCacheCacheProvider() {
        LOGGER.info("Your profile is Production.");
        return new EhCacheCacheProvider();
    }

    @Profile("dev")
    @Bean
    public CacheProvider fileCacheProvider() {
        LOGGER.info("Your profile is Development.");
        return new FileCacheProvider();
    }
}
