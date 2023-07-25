package se.moeser.javacleanscaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaCleanScaffoldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configContext = SpringApplication.run(JavaCleanScaffoldApplication.class, args);
    }

}
