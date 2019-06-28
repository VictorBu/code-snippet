package com.karonda.twittersnowflake.runner;

import com.karonda.twittersnowflake.utils.Snowflake;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Snowflake snowflake = new Snowflake(2, 5);

//        long st = snowflake.timestampGen();
//        System.out.println(st);
        while (true){

            System.out.println(snowflake.nextId());

//            TimeUnit.SECONDS.sleep(1);
        }
    }
}
