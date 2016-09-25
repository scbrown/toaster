package name.brown.steve
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
class StubTests {

    @Autowired
    private DependencyService service

    @Test
    void "it loads"(){
        assert this.service.data == "Hello World"
    }

}
