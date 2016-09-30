package name.brown.steve

import name.brown.steve.dto.CacheJob
import name.brown.steve.dto.SeedCallContext
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
class CacheRequestServiceIntTests {

    @Autowired
    CacheRequestService cacheRequestService

    SeedCallContext seedCallContext

    @Before
    void doBefore(){
        seedCallContext = TestUtil.setupSeedCallContext()
    }

    @Test
    void "test cache request calls use the correct watchVariableResults"(){
        def job = new CacheJob(urls:
                [
"http://localhost:8383/cachingcall/{${TestUtil.SEED_CALL1}.albums.title}/{${TestUtil.SEED_CALL2}.albums.track1}/{${TestUtil.SEED_CALL3}.albums.track2}/"
                ], body: null)

        cacheRequestService.doCacheCall(job, seedCallContext)
        def expected = "http://localhost:8383/cachingcall/${->TestUtil.WATCH_RESULT1[index]}/${->TestUtil.WATCH_RESULT2[index]}/${->TestUtil.WATCH_RESULT3[0]}/"
        findAll(putRequestedFor(urlMatching("/cachingcall/.*"))).size().times{ index ->
            println "expected: $expected"
            verify(getRequestedFor(urlEqualTo(expected)))
        }
    }
}
