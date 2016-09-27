package name.brown.steve

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableTask
class HeatApplication {

    @Bean
    public HeatTask heatTask(){
        return new HeatTask()
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	static void main(String[] args) {
		SpringApplication.run HeatApplication, args
	}
	

}
