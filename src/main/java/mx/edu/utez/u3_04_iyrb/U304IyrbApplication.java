package mx.edu.utez.u3_04_iyrb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class U304IyrbApplication {

    private static final Logger logger = LoggerFactory.getLogger(U304IyrbApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando la aplicación U304IyrbApplication");
        SpringApplication.run(U304IyrbApplication.class, args);
    }

}
