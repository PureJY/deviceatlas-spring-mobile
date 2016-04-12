# DeviceAtlas Detection Module for Spring Mobile #

## Introduction ##
This library implements the device abstractions provided by Spring Mobile 
(Device Resolvers and Device) using DeviceAtlas intelligence. It provides 
accurate data to identify traffic as mobile, tablet, normal or robot.


## DeviceAtlas Cloud Module ##
The DeviceAtlas Cloud API provides an easy, fault tolerant way to retrieve 
device information from the DeviceAtlas Cloud service. This library extends this
to expose the DeviceAtlas properties in Spring.


### Requirements ###

1. A DeviceAtlas Cloud licence key. Please see https://deviceatlas.com/resources/getting-started-cloud
2. Spring Mobile https://github.com/spring-projects/spring-mobile .


### Getting started ###
The DeviceAtlas Cloud API, DeviceAtlas Spring Extension and Spring Mobile can 
all be found in Maven Central. The easiest and quickest way for Maven/Gradle 
users is to add the dependencies as follows:

```shell
# Maven
...
<dependencies>
    <dependency>
        <groupId>com.deviceatlas</groupId>
        <artifactId>deviceatlas-cloud-java-client</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.deviceatlas</groupId>
        <artifactId>deviceatlas-spring-mobile-extension</artifactId>
        <version>0.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.mobile</groupId>
        <artifactId>spring-mobile-device</artifactId>
        <version>1.1.5.RELEASE</version>
    </dependency>
</dependencies>
...


# Gradle
...
dependencies {
    ...
    compile "com.deviceatlas:deviceatlas-cloud-java-client:2.0.0"
    compile "com.deviceatlas:deviceatlas-spring-mobile-extension:0.1.0"
    compile "org.springframework.boot:spring-mobile-device:1.1.5.RELEASE"
    ...
}
...

```


### Configuration in Spring ###
To use the default configuration, add the "com.deviceatlas.springmobile" to the 
configuration component scan. This will initialise the DeviceAtlas Spring 
Extension.

For example, in a Spring Application class:

```Java
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.deviceatlas.springmobile"})
public class Application {
    public static void main(String [] args) {
        SpringApplication.run(Application.class);
    }
}
```
This will pickup the com.deviceatlas.springmobile.Config class to do the 
following:

1. Create a Bean of the DeviceAtlas Cloud Client.
2. Create an EhCacheCacheProvider Bean for "prod" environments or a 
FileCacheProvider Bean for "dev" environments.

The extension will look for properties in the Spring application.yml or 
application.properties files with prefix of "deviceatlas". See section below on 
Spring Profiles for different "prod" and "dev" configuration.

The following properties can be set:

| Name                         | Mandatory |
|------------------------------|-----------|
| licenceKey                   |    Yes    |
| autoServerRankingNumRequests |    No     |
| autoServerRankingMaxFailures |    No     |
| autoServerRanking            |    No     |
| serverRankingLifetime        |    No     |
| useCache                     |    No     |
| useClientCookie              |    No     |
| cloudServiceTimeout          |    No     |
| endPointUrls                 |    No     |



#### Spring profiles ####
This extension uses Spring profiles for configuration, see the 
[Spring Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html).

The example application provides sample configuration profiles for "prod" and 
"dev" environments: See [application-dev.yml](deviceatlas-spring-mobile-example/src/WebApp/src/main/resources/application-dev.yml) 
and [application-prod.yml](deviceatlas-spring-mobile-example/src/WebApp/src/main/resources/application-prod.yml) 
in src/WebApp/src/main/resources/.


### Caching ###
The default Config class uses EhCache as the Cache Provider for the "prod" 
profile. To use a different Cache Provider create a custom Config class and 
provide it in the component scan instead of the com.deviceatlas.springmobile.Config 
class. 

Other bundled cache providers are:

- EhCacheCacheProvider (the default).
- MemcachedCacheProvider.
- FileCacheProvider.
- SimpleCacheProvider.
- MockCacheProvider (no cache).

In addition, a SpringCacheDeviceAtlasAdaptor is provided for integration into 
existing Spring Caching strategies. 

```java
import org.springframework.cache;
import org.springframework.cache.ehcache.EhCacheCache;
...
@Bean
public Cache springCache() {
    return new EhCacheCache(ehCache);
}

...
@Autowired
public Cache springCache;
...
@Bean
public CacheProvider springCacheAdaptor() {
    return new SpringCacheDeviceAtlasAdaptor();
}
```



## Example ##

A Spring Boot Application is provided as an example, using the components 
described above. It uses different configuration profiles for "prod" and "dev" 
environments. These can be found in src/WebApp/src/main/resources/. 

The necessary components are called from the Application's context, the 
configuration, the device resolver etc. See the files in the root of the example 
application: [Example files](deviceatlas-spring-mobile-example/src/WebApp/src/main/java/com/deviceatlas/cloud/example/).



## Device Methods ##
The following methods are available from the DeviceAtlasDevice class to assist 
in making decisions based on the visiting device's capabilities:


| Return type                   | Method's name                         | Description                                                                                              |
|-------------------------------|---------------------------------------|----------------------------------------------------------------------------------------------------------|
| boolean                       | isTablet                              | If the detected incoming request is a Tablet, it will return true                                        |
| boolean                       | isMobile                              | If the detected incoming request is not a Tablet and not a Desktop, it will return true                  |
| boolean                       | isNormal                              | If the detected incoming request is not a Tablet and not a Mobile, it will return true                   |
| Boolean                       | isNonHuman                            | If the detected incoming request is a Bot (spam, downloader, ...), it will return true (*)               |
| DeviceAtlasDevicePlatform enum| daDevice.getDeviceAtlasDevicePlatform | Returns the platform for the visiting device.                                                            |


(*) Available for Cloud Standard / Premium licenses, more informations can be 
found at the [DeviceAtlas Pricing & Trial](https://deviceatlas.com/pricing-and-trial)
page.

When configured correctly, the DeviceAtlasDevice class is automatically 
available from Controller methods, for example:

```java
public class ApplicationController {
    @RequestMapping("/")
    public String index(DeviceAtlasDevice daDevice, Model model) {
        if (daDevice.isMobile()) {
            model.addAttribute("deviceType", DeviceType.MOBILE);
        } else if (daDevice.isTablet()) {
            model.addAttribute("deviceType", DeviceType.TABLET);
        }
        
        model.addAttribute("os", daDevice.getDeviceAtlasDevicePlatform());
        return "index";
    }
}
```


## Compiling from source ##
To build the DeviceAtlas Spring Extension from source use the provided Maven or 
Gradle build scripts inside deviceatlas-spring-mobile-extension folder.

```shell
# With Maven

% mvn package

# With Gradle

% path_to_gradle/gradle build

```
