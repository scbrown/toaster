package name.brown.steve
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit4.SpringRunner

import static com.github.tomakehurst.wiremock.client.WireMock.*

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383)//, stubs="classpath:/stubs/resource.json")
class StubTests {

    @Autowired
    private DependencyService service

    @Test
    void "it loads"(){
        stubFor(get(urlEqualTo("/resource"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain").withBody("Hello World")))
        assert this.service.data == "Hello World"
    }

}
