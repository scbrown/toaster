package name.brown.steve

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DependencyService {

    @Value('${app.baseUrl:http://localhost:8383}')
    private String base;

    @Autowired
    private RestTemplate restTemplate

    public String getData(){
        return restTemplate.getForEntity(base + "/resource", String.class).body
    }
}
