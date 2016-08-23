package com.example;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.File;


@SpringBootApplication
@EnableTask
public class ThumbnailProducerApplication {

    @Autowired
    private Environment env;

    private static final Log logger = LogFactory.getLog(ThumbnailProducerApplication.class);

    @Bean
    public ThumbnailProducerTask timeStampTask() {
        return new ThumbnailProducerTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(ThumbnailProducerApplication.class, args);
    }

    public class ThumbnailProducerTask implements CommandLineRunner {

        @Override
        public void run(String... strings) throws Exception {
            logger.info("=======START=======");

            Thumbnails.of(new File(env.getProperty("payload")))
                    .size(200, 200)
                    .toFiles(new File("/data/thumbnails"), Rename.PREFIX_DOT_THUMBNAIL);

            logger.info("=======COMPLETE=======");
        }
    }
}
