package name.brown.steve

import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedJob
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
@DirtiesContext
class SeedServiceTests {

    @Autowired
    SeedService service

    @Test
    void "test getting specified watch variables"(){
        String watchVariable = "albums.title"
        SeedCall seedCall = new SeedCall(
                name: "seedCall", url: "http://localhost:8383/seed", watchVariables: [watchVariable])
        SeedJob job = new SeedJob(seedCalls: [seedCall])
        SeedCallContext context = service.getSeed(job)
        println "test context: $context"
        assert context.contextData.get(seedCall.name).watchVariableResult.get(watchVariable)?.contains("andycapmusic")
    }

   /* @Test
    void "test using watch variables to get other watch variables"(){
        String watchVariable1 = "albums.title"
        String watchVariable2 = "albums.track1"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seed", watchVariables: [watchVariable1]
        )
        SeedCall seedCall2 = new SeedCall(
                name: "seedCall2", url: "http://localhost:8383/seed/{seedCall1.albums.title}", watchVariables: [watchVariable2]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1, seedCall2])
        SeedCallContext context = service.getSeed(job)
        assert context.contextData.get(seedCall1.name).watchVariableResult.get(watchVariable1).contains("andycapmusic")
    }*/

    @Test
    void "test getting multiple watch variables with the same name"(){
        String watchVariable1 = "albums.title"
        String watchVariable2 = "albums.title"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seed", watchVariables: [watchVariable1]
        )
        SeedCall seedCall2 = new SeedCall(
                name: "seedCall2", url: "http://localhost:8383/seed2", watchVariables: [watchVariable2]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1, seedCall2])
        SeedCallContext context = service.getSeed(job)
        assert context.contextData.get(seedCall1.name).watchVariableResult.get(watchVariable1).contains("andycapmusic")
        assert context.contextData.get(seedCall1.name).watchVariableResult.get(watchVariable1).contains("NOTandycapmusic")
    }
}
