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

import com.deviceatlas.springmobile.constants.DeviceAtlasDevicePlatform;
import com.deviceatlas.cloud.deviceidentification.client.Client;
import com.deviceatlas.cloud.deviceidentification.client.Property;
import com.deviceatlas.cloud.deviceidentification.client.Properties;
import com.deviceatlas.cloud.deviceidentification.client.ClientConstants;
import com.deviceatlas.cloud.deviceidentification.client.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class DeviceAtlasDeviceResolver implements DeviceResolver {
    @Autowired
    private Client client;
    private Properties deviceAtlasProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceAtlasDeviceResolver.class);

    @Override
    public Device resolveDevice(HttpServletRequest request) {
        DeviceAtlasDevice daDevice = new DeviceAtlasDevice();

        try {
            initDeviceAtlasProperties(request);
            final String PRIMARY_HARDWARE_TYPE_PROPERTY_NAME = "primaryHardwareType";
            boolean isTablet = false;
            boolean isMobile = false;
            boolean isNormal = false;
            boolean isNonHuman = isNonHuman();
            Property devicePropertyObj = deviceAtlasProperties.get(PRIMARY_HARDWARE_TYPE_PROPERTY_NAME);

            if (devicePropertyObj != null) {
                final String PRIMARY_HARDWARE_TYPE_IS_TABLET = "Tablet";
                final String PRIMARY_HARDWARE_TYPE_IS_MOBILE_PHONE = "Mobile Phone";
                final String PRIMARY_HARDWARE_TYPE_IS_DESKTOP = "Desktop";
                final String deviceProperty = devicePropertyObj.asString();
                isTablet = deviceProperty.equals(PRIMARY_HARDWARE_TYPE_IS_TABLET);
                isMobile = deviceProperty.equals(PRIMARY_HARDWARE_TYPE_IS_MOBILE_PHONE);
                isNormal = deviceProperty.equals(PRIMARY_HARDWARE_TYPE_IS_DESKTOP);
            }

            if (!isTablet && !isMobile && !isNormal && !isNonHuman) {
                /* default to normal */
                isNormal = true;
            }

            DevicePlatform devicePlatform = getDevicePlatform();
            DeviceAtlasDevicePlatform deviceAtlasDevicePlatform = getDeviceAtlasDevicePlatform();
            daDevice.setNonHuman(isNonHuman);
            daDevice.setMobile(isMobile);
            daDevice.setTablet(isTablet);
            daDevice.setNormal(isNormal);
            daDevice.setDevicePlatform(devicePlatform);
            daDevice.setDeviceAtlasDevicePlatform(deviceAtlasDevicePlatform);
        } catch (ClientException e) {
            LOGGER.error("resolveDevice failed: {}", e.getMessage());
        }

        return daDevice;
    }

    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor(this);
    }

    public Properties initDeviceAtlasProperties() {
        return deviceAtlasProperties;
    }

    public void setDeviceAtlasProperties(Properties deviceAtlasProperties) {
        this.deviceAtlasProperties = deviceAtlasProperties;
    }

    private DevicePlatform getDevicePlatform() {
        DeviceAtlasDevicePlatform devicePlatform = getDeviceAtlasDevicePlatform();
        if (devicePlatform == DeviceAtlasDevicePlatform.ANDROID) {
            return DevicePlatform.ANDROID;
        } else if (devicePlatform == DeviceAtlasDevicePlatform.IOS) {
            return DevicePlatform.IOS;
        }
        return DevicePlatform.UNKNOWN;
    }

    private DeviceAtlasDevicePlatform getDeviceAtlasDevicePlatform() {

        DeviceAtlasDevicePlatform devicePlatform = DeviceAtlasDevicePlatform.UNKNOWN;

        final String osNamePropertyName = "osName";

        if (!deviceAtlasProperties.containsKey(osNamePropertyName)) {
            return devicePlatform;
        }

        String osName = deviceAtlasProperties.get(osNamePropertyName).asString();

        final String OS_NAME_ANDROID = "Android";
        final String OS_NAME_BADA = "Bada";
        final String OS_NAME_IOS = "iOS";
        final String OS_NAME_SYMBIAN = "Symbian";
        final String OS_NAME_WINDOWS_MOBILE = "Windows Mobile";
        final String OS_NAME_WINDOWS_PHONE = "Windows Phone";
        final String OS_NAME_WINDOWS_RT = "Windows RT";
        final String OS_NAME_WEBOS = "webOS";
        final String OS_NAME_OS_X = "OS X";
        final String OS_NAME_RIM = "RIM";
        final String linuxOsPrefix = "Linux";
        final String windowsOsPrefix = "Windows";

        // Check first wheter it is a mobile device platform
        if (osName.equals(OS_NAME_ANDROID)) {
            return DeviceAtlasDevicePlatform.ANDROID;
        } else if (osName.equals(OS_NAME_BADA)) {
            return DeviceAtlasDevicePlatform.BADA;
        } else if (osName.equals(OS_NAME_IOS)) {
            return DeviceAtlasDevicePlatform.IOS;
        } else if (osName.equals(OS_NAME_SYMBIAN)) {
            return DeviceAtlasDevicePlatform.SYMBIAN;
        } else if (osName.equals(OS_NAME_WINDOWS_MOBILE)) {
            return DeviceAtlasDevicePlatform.WINDOWS_MOBILE;
        } else if (osName.equals(OS_NAME_WINDOWS_PHONE)) {
            return DeviceAtlasDevicePlatform.WINDOWS_PHONE;
        } else if (osName.equals(OS_NAME_WINDOWS_RT)) {
            return DeviceAtlasDevicePlatform.WINDOWS_RT;
        } else if (osName.equals(OS_NAME_WEBOS)) {
            return DeviceAtlasDevicePlatform.WEBOS;
        } else if (osName.equals(OS_NAME_OS_X)) {
            return DeviceAtlasDevicePlatform.OS_X;
        } else if (osName.equals(OS_NAME_RIM)) {
            return DeviceAtlasDevicePlatform.BLACKBERRY;
        } else if (osName.startsWith(linuxOsPrefix)) {
            return DeviceAtlasDevicePlatform.LINUX;
        } else if (osName.startsWith(windowsOsPrefix)) {
            return DeviceAtlasDevicePlatform.WINDOWS;
        }

        return devicePlatform;
    }

    private boolean isNonHuman() {
        boolean isNonHuman = false;
        final String[] NON_HUMAN_PROPERTIES = {"isChecker", "isDownloader", "isFilter", "isSpam", "isFeedReader", "isRobot"};
        for (String daPropertyName: NON_HUMAN_PROPERTIES) {
            if (deviceAtlasProperties.contains(daPropertyName, true)) {
                isNonHuman = true;
                break;
            }
        }
        return isNonHuman;
    }

    // TODO: should this object creation be delegated to Spring as a Bean?
    private void initDeviceAtlasProperties(HttpServletRequest request) throws ClientException {
        deviceAtlasProperties = client.getResult(request).getProperties();
    }

}
