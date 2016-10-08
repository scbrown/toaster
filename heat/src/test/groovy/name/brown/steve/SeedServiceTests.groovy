package name.brown.steve
import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedJob
import name.brown.steve.exception.WatchVariableNotFoundException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

import static com.github.tomakehurst.wiremock.client.WireMock.*

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
@DirtiesContext
class SeedServiceTests {

    @Autowired
    SeedService service

    @Before
    void doBefore(){
        resetAllRequests()
    }

    @Test
    void "test getting specified watch variables"(){
        String watchVariable = "albums.title"
        SeedCall seedCall = new SeedCall(
                name: "seedCall", url: "http://localhost:8383/seed", watchVariables: [watchVariable])
        SeedJob job = new SeedJob(seedCalls: [seedCall])

        SeedCallContext context = service.getSeed(job)

        assert context.contextData.get(seedCall.name).watchVariableResult.get(watchVariable)?.contains("andycapmusic")
    }

    @Test
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
        assert context.contextData.get(seedCall2.name).watchVariableResult.get(watchVariable2).contains("evildead")
    }

    @Test
    void "test getting multiple watch variables with the same name"(){
        String watchVariable1 = "albums.title"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seed", watchVariables: [watchVariable1]
        )
        SeedCall seedCall2 = new SeedCall(
                name: "seedCall2", url: "http://localhost:8383/seed2", watchVariables: [watchVariable1]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1, seedCall2])

        SeedCallContext context = service.getSeed(job)

        assert context.contextData.get(seedCall1.name).watchVariableResult.get(watchVariable1).contains("andycapmusic")
        assert context.contextData.get(seedCall2.name).watchVariableResult.get(watchVariable1).contains("NOTandycapmusic")
    }

    @Test
    void "test seed urls don't get called multiple times when there are no watch variables"(){
        String watchVariable1 = "albums.title"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seed", watchVariables: [watchVariable1]
        )
        SeedCall seedCall2 = new SeedCall(
                name: "seedCall2", url: "http://localhost:8383/seed2", watchVariables: [watchVariable1]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1, seedCall2])

        SeedCallContext context = service.getSeed(job)

        verify(exactly(1), getRequestedFor(urlEqualTo("/seed2")))
    }

    @Test
    void "test seed urls aren't called more than they need to be when they contain watch variables"(){
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

        verify(exactly(1), getRequestedFor(urlEqualTo("/seed/andycapmusic")))
    }

    @Test
    void "test watch variables that result in a list result in multiple watch values instead of one list"(){
        String watchVariable1 = "albums.tags"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seedWithMultiple", watchVariables: [watchVariable1, watchVariable1]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1])

        SeedCallContext context = service.getSeed(job)

        assert context.contextData.get(seedCall1.name).watchVariableResult.get(watchVariable1).size() == 2
    }

    @Test
    void "test seed urls aren't called multiple times when there are multiple watch variables the 2nd url doesn't contain"(){
        String watchVariable1 = "albums.title"
        String watchVariable2 = "albums.tags" //not used in 2nd seed call
        String watchVariable3 = "albums.track1"
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seedWithMultiple", watchVariables: [watchVariable1, watchVariable2]
        )
        SeedCall seedCall2 = new SeedCall(
                name: "seedCall2", url: "http://localhost:8383/seed/{seedCall1.albums.title}", watchVariables: [watchVariable3]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1, seedCall2])

        SeedCallContext context = service.getSeed(job)

        verify(exactly(1), getRequestedFor(urlEqualTo("/seed/andycapmusic")))
    }

    @Test(expected = WatchVariableNotFoundException)
    void "test gracefully handling watch variable not found"(){
        String watchVariable1 = "albums.title"
        String watchVariable2 = "albums.tags.not.exist" //this won't be found
        SeedCall seedCall1 = new SeedCall(
                name: "seedCall1", url: "http://localhost:8383/seedWithMultiple", watchVariables: [watchVariable1, watchVariable2]
        )
        SeedJob job = new SeedJob(seedCalls: [seedCall1])

        SeedCallContext context = service.getSeed(job)
    }
}
