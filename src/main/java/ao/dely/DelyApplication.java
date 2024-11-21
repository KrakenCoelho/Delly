package ao.dely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DelyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelyApplication.class, args);
	}

}
