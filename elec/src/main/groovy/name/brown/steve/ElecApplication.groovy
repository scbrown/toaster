package name.brown.steve

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.task.launcher.annotation.EnableTaskLauncher

@SpringBootApplication
@EnableTaskLauncher
class ElecApplication {

	static void main(String[] args) {
		SpringApplication.run ElecApplication, args
	}
}
