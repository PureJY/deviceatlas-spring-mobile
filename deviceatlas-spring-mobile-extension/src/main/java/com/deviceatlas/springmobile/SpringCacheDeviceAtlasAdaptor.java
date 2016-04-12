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

import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheException;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.util.ArrayList;
import java.util.List;

public class SpringCacheDeviceAtlasAdaptor implements CacheProvider {

    @Autowired
    private Cache deviceAtlasCache;

    public Cache getCache() {
        return deviceAtlasCache;
    }

    @Override
    public <T> T get(String key) throws CacheException {
        return (T) this.deviceAtlasCache.get(key).get();
    }

    @Override
    public <T> void set(String key, T value) throws CacheException {
        this.deviceAtlasCache.putIfAbsent(key, value);
    }

    @Override
    public void remove(String key) throws CacheException {
        this.deviceAtlasCache.evict(key);
    }

    @Override
    public void clear() {
        this.deviceAtlasCache.clear();
    }

    @Override
    public void shutdown() {
        // No shutdown's matter with Spring's cache
    }

    @Override
    public void setExpiry(int expiration) {
        // No expiry matter with Spring's cache
    }

    @Override
    public List<String> getKeys() {
        return new ArrayList<String>();
    }
}
