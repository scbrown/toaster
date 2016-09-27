package name.brown.steve
import name.brown.steve.dto.CacheJob
import name.brown.steve.dto.SeedCallContext
import org.junit.Before
import org.junit.Test
import org.springframework.web.client.RestTemplate

class CacheRequestServiceTests {

    CacheRequestService cacheRequestService

    SeedCallContext seedCallContext

    def calledUrls = []
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
    }

    @Test
    void "test cache request calls use seeded variables"(){
         CacheJob job = new CacheJob(url: 'http://localhost/listen/{albums.title}/{albums.track1}/{albums.track2}/', body: null)

        cacheRequestService.doCacheCall(job, seedCallContext)
        println "template calls: ${calledUrls}"
        assert calledUrls.size() == 2
     }
}
