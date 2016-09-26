package name.brown.steve

import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedJob
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
class SeedServiceTests {

    @Autowired
    SeedService service

    @Test
    void "test getting specified watch variables"(){
        String watchVariable = "albums.title"
        SeedCall seedCall = new SeedCall(
                url: "http://localhost:8383/seed", watchVariables: [watchVariable])
        SeedJob job = new SeedJob(seedCalls: [seedCall])
        SeedCallContext context = service.getSeed(job)
        println "test context: $context"
        assert context.contextData.get(seedCall).watchVariableResult.get(watchVariable).contains("andycapmusic")
    }
}
