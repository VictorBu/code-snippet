package com.karonda.sequentialuuid.task;

import com.karonda.sequentialuuid.utils.SequentialUuidHexGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SequentialUuidHexGeneratorTask implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        while (true){
            System.out.println(SequentialUuidHexGenerator.generate());
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
