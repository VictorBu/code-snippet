package com.karonda.watchdog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ShellTask {

    private static final Logger logger = LoggerFactory.getLogger(ShellTask.class);

    @Autowired
    private ShellProperties shellProperties;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void start() throws IOException {
        executeShell(shellProperties.getStartupFileName());
    }

    private void executeShell(String fileName) throws IOException {

        String fileFullName = shellProperties.getFullName(fileName);
        File file = new File(fileFullName);
        if(!file.exists()) {
            logger.error("file {} not existed!", fileFullName);
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(fileFullName);
        processBuilder.directory(new File(shellProperties.getDirectory()));

        Process process = processBuilder.start();

//        String input;
//        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        while ((input = stdInput.readLine()) != null) {
//            logger.info(input);
//        }
//        while ((input = stdError.readLine()) != null) {
//            logger.error(input);
//        }

        int runningStatus = 0;
        try {
            runningStatus = process.waitFor();
        } catch (InterruptedException e) {
            logger.error("shell", e);
        }

        if(runningStatus != 0) {
            logger.error("failed.");
        }else {
            logger.info("success.");
        }
    }
}
