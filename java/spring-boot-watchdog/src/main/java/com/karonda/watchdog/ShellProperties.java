package com.karonda.watchdog;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "shell")
public class ShellProperties {

    private String directory;
    private String startupFileName;
    private String restartFileName;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getStartupFileName() {
        return startupFileName;
    }

    public void setStartupFileName(String startupFileName) {
        this.startupFileName = startupFileName;
    }

    public String getRestartFileName() {
        return restartFileName;
    }

    public void setRestartFileName(String restartFileName) {
        this.restartFileName = restartFileName;
    }

    public String getFullName(String fileName) {
        return directory + File.separator + fileName;
    }
}
