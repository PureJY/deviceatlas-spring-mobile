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

package com.deviceatlas.springmobile.service;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import com.deviceatlas.springmobile.constants.DeviceAtlasDevicePlatform;

public class DeviceAtlasDevice implements Device {
    private boolean isMobile;
    private boolean isTablet;
    private boolean isNormal;
    private boolean isNonHuman;
    private DevicePlatform devicePlatform;
    private DeviceAtlasDevicePlatform deviceAtlasDevicePlatform;

    /**
     * If the detected device is a bot
     *
     * @return boolean
     */
    public boolean isNonHuman() {
        return isNonHuman;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public void setNonHuman(boolean isnonHuman) {
        isNonHuman = isnonHuman;
    }

    public void setDevicePlatform(DevicePlatform devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public void setDeviceAtlasDevicePlatform(DeviceAtlasDevicePlatform devicePlatform) {
        this.deviceAtlasDevicePlatform = devicePlatform;
    }

    @Override
    public boolean isMobile() {
        return isMobile;
    }

    @Override
    public boolean isTablet() {
        return isTablet;
    }

    @Override
    public boolean isNormal() {
        return isNormal;
    }

    @Override
    public DevicePlatform getDevicePlatform() {
        return devicePlatform;
    }

    public DeviceAtlasDevicePlatform getDeviceAtlasDevicePlatform() {
        return deviceAtlasDevicePlatform;
    }

    @Override
    public int hashCode() {
        int hash = 61;
        hash += ((Boolean)this.isMobile()).hashCode() + ((Boolean)this.isTablet()).hashCode() +
                ((Boolean)this.isNormal()).hashCode() + ((Boolean)this.isNonHuman()).hashCode() + 
                this.getDevicePlatform().hashCode() + this.getDeviceAtlasDevicePlatform().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DeviceAtlasDevice)) {
            return false;
        }

        final DeviceAtlasDevice other = (DeviceAtlasDevice)o;

        return (this.isMobile() == other.isMobile() && this.isTablet() == other.isTablet() &&
                this.isNormal() == other.isNormal() && this.isNonHuman() == other.isNonHuman() &&
                this.getDevicePlatform() == other.getDevicePlatform() && this.getDeviceAtlasDevicePlatform() == other.getDeviceAtlasDevicePlatform());
    }
}
