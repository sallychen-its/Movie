package com.movie_admin.service.storage;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("storage")
public class StorageProperties {


    // private String location = "/usr/local/tomcat/webapps/content/upload-dir";
    private String location = "src/main/webapp/content/upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
