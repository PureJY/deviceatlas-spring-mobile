package com.deviceatlas.springmobile.service;

import com.deviceatlas.cloud.deviceidentification.client.Client;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.CacheProvider;
import com.deviceatlas.cloud.deviceidentification.cacheprovider.SimpleCacheProvider;
import com.deviceatlas.springmobile.Config;
import com.deviceatlas.springmobile.DeviceAtlasPreferences;
import com.deviceatlas.springmobile.constants.DeviceAtlasDevicePlatform;
import com.deviceatlas.springmobile.service.DeviceAtlasDevice;
import com.deviceatlas.springmobile.service.DeviceAtlasDeviceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={TestConfig.class})
public class DADeviceResolverTest {
    @Autowired
    private DeviceResolver deviceAtlasDeviceResolver;
    @Autowired
    private Client deviceIdentificator;
    private MockHttpServletRequest request;

    private DeviceAtlasDevice deviceAtlasDevice;

    @Before
    public void setUp() throws Exception {
        deviceAtlasDevice = new DeviceAtlasDevice();
        request = new MockHttpServletRequest();
        deviceIdentificator.clearCache();
    }

    @Test
    public void testUnknown() throws Exception {
        deviceAtlasDevice.setNonHuman(true);
        deviceAtlasDevice.setDevicePlatform(DevicePlatform.UNKNOWN);
        deviceAtlasDevice.setDeviceAtlasDevicePlatform(DeviceAtlasDevicePlatform.UNKNOWN);

        request.addHeader("user-agent", "Googlebot-Image/1.0");
        DeviceAtlasDevice deviceAtlasDevice1 = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(deviceAtlasDevice, deviceAtlasDevice1);

    }

    @Test
    public void testIos() throws Exception {
        request.addHeader("user-agent", "iPhone");
        deviceAtlasDevice = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(DevicePlatform.IOS, deviceAtlasDevice.getDevicePlatform());
        DeviceAtlasDevice deviceAtlasDevice1 = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(deviceAtlasDevice, deviceAtlasDevice1);
    }

    @Test
    public void testAndroid() throws Exception {
        request.addHeader("user-agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; Nexus 7 Build/KOT49H)");
        deviceAtlasDevice = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(DevicePlatform.ANDROID, deviceAtlasDevice.getDevicePlatform());
        DeviceAtlasDevice deviceAtlasDevice1 = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(deviceAtlasDevice, deviceAtlasDevice1);
    }

    @Test
    public void testWin() throws Exception {
        request.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.2; ARM; Trident/7.0; Touch; rv:11.0; WPDesktop; Lumia 640 Dual SIM) like Gecko");
        deviceAtlasDevice = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(DeviceAtlasDevicePlatform.WINDOWS_PHONE, deviceAtlasDevice.getDeviceAtlasDevicePlatform());
        DeviceAtlasDevice deviceAtlasDevice1 = (DeviceAtlasDevice)deviceAtlasDeviceResolver.resolveDevice(request);

        assertEquals(deviceAtlasDevice, deviceAtlasDevice1);
    }
}
