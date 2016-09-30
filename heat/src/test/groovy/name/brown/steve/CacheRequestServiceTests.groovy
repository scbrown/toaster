package name.brown.steve
import name.brown.steve.dto.CacheJob
import name.brown.steve.dto.SeedCallContext
import org.junit.Before
import org.junit.Test
import org.springframework.web.client.RestTemplate

class CacheRequestServiceTests {

    CacheRequestService cacheRequestService

    SeedCallContext seedCallContext

    def calledUrls
    def restTemplate = [
        getForObject: { url, clazz, variables ->
            println "called url: $url variables: $variables"
            calledUrls << url
        }
    ]

    @Before
    void doBefore(){
        cacheRequestService = new CacheRequestService()
        cacheRequestService.restTemplate = restTemplate as RestTemplate
        seedCallContext = TestUtil.setupSeedCallContext()
        calledUrls = []
    }

    @Test
    void "test cache request calls the correct number of urls based on watchVariableResults"(){
         def job = new CacheJob(urls: ['dont care'], body: null)

        cacheRequestService.doCacheCall(job, seedCallContext)
        assert calledUrls.size() == 2
    }

}
