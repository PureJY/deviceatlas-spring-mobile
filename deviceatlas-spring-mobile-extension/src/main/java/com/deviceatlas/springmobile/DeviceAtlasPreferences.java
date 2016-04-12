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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;

import java.util.List;

@Component
@ConfigurationProperties(prefix="deviceatlas")
public class DeviceAtlasPreferences {

    @NotNull
    private String licenceKey;

    private int autoServerRankingNumRequests;
    private int autoServerRankingMaxFailures;
    private boolean autoServerRanking;
    private int serverRankingLifetime;
    private boolean useCache;
    private boolean useClientCookie;
    private int cloudServiceTimeout;
    private List<String> endPointUrls;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceAtlasPreferences.class);
    /**
     * Returns the set licence key
     *
     * @return String
     */
    public String getLicenceKey() {
        return licenceKey;
    }

    public int getAutoServerRankingNumRequests() {
        return autoServerRankingNumRequests;
    }

    public int getAutoServerRankingMaxFailures() {
        return autoServerRankingMaxFailures;
    }

    public int getServerRankingLifetime() {
        return serverRankingLifetime;
    }

    public boolean getUseCache() {
        return useCache;
    }

    public boolean getUseClientCookie() {
        return useClientCookie;
    }

    public int getCloudServiceTimeout() {
        return cloudServiceTimeout;
    }

    /**
     * Returns the list of DeviceAtlas cloud service endpointsUrls
     *
     * @return List
     */
    public List getEndPointUrls() {
        return this.endPointUrls;
    }

    /**
     * Sets the user's licence key for the DeviceAtlas device resolving
     *
     * @param licenceKey String with the DA Cloud licence key
     */
    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public void setAutoServerRankingNumRequests(int autoServerRankingNumRequests) {
        LOGGER.info("setAutoServerRankingNumRequests:\n" + autoServerRankingNumRequests);
        this.autoServerRankingNumRequests = autoServerRankingNumRequests;
    }

    public void setAutoServerRankingMaxFailures(int autoServerRankingMaxFailures) {
        LOGGER.info("setAutoServerRankingMaxFailures:\n" + autoServerRankingMaxFailures);
        this.autoServerRankingMaxFailures = autoServerRankingMaxFailures;
    }

    public void setServerRankingLifetime(int serverRankingLifetime) {
        LOGGER.info("setServerRankingLifetime:\n" + serverRankingLifetime);
        this.serverRankingLifetime = serverRankingLifetime;
    }

    public void setUseCache(boolean useCache) {
        LOGGER.info("setUseCache:\n" + useCache);
        this.useCache = useCache;
    }

    public void setUseClientCookie(boolean useClientCookie) {
        LOGGER.info("setUseClientCookie:\n" + useClientCookie);
        this.useClientCookie = useClientCookie;
    }

    public void setCloudServiceTimeout(int cloudServiceTimeout) {
        LOGGER.info("setCloudServiceTimeout:\n" + cloudServiceTimeout);
        this.cloudServiceTimeout = cloudServiceTimeout;
    }

    /**
     * Set the DeviceAtlas cloud service endpointsUrls
     *
     * @param endPointUrls List
     */
    public void setEndPointUrls(List<String> endPointUrls) {
        this.endPointUrls = endPointUrls;
    }

    /**
     * Gives the state of the servers auto ranking
     *
     * @return boolean
     */
    public boolean getAutoServerRanking() {
        return autoServerRanking;
    }

    /**
     * Sets the servers auto ranking state, if
     * true then the servers will be ordered from
     * the most reachable
     *
     * @param autoServerRanking boolean object
     */
    public void setAutoServerRanking(boolean autoServerRanking) {
        this.autoServerRanking = autoServerRanking;
    }

}
