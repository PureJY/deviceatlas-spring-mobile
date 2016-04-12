package com.deviceatlas.springmobile.constants;

public enum DeviceAtlasDevicePlatform {

    // Mobile device platforms
    ANDROID("Android"),
    BADA("Bada"),
    IOS("iOS"),
    BLACKBERRY("BlackBerry"),
    SYMBIAN("Symbian"),
    WINDOWS_MOBILE("Windows Mobile"),
    WINDOWS_PHONE("Windows Phone"),
    WINDOWS_RT("Windows RT"),
    WEBOS("webOS"),

    // Desktop device platforms
    WINDOWS("Windows"),
    LINUX("Linux"),
    OS_X("OS X"),

    // Unknown device platform, e.g. bot
    UNKNOWN("Unknown");

    private final String devicePlatform;

    DeviceAtlasDevicePlatform(final String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    @Override
    public String toString() {
        return devicePlatform;
    }

}
