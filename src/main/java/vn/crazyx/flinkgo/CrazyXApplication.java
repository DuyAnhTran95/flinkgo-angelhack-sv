package vn.crazyx.flinkgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import vn.crazyx.flinkgo.config.MVCConfig;


@SpringBootApplication
@Import({ MVCConfig.class })
@ComponentScan(basePackages = { "vn.crazyx.flinkgo" })
@EnableJpaRepositories(basePackages = { "vn.crazyx.flinkgo.dao" })
public class CrazyXApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrazyXApplication.class, args);
	}

}
