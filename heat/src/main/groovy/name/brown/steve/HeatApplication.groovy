package name.brown.steve
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableTask
class HeatApplication {

	private static final Log log = LogFactory.getLog(HeatApplication.class)

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
	
	public class HeatTask implements CommandLineRunner{
        @Autowired
        DependencyService dependencyService
        public void run(String... strings) throws Exception {
            log.info "oh hai!"
            log.info "result: ${dependencyService.data}"
         }
    }
}
