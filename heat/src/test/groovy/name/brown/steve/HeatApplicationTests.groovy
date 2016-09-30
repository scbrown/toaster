package name.brown.steve

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

import static com.github.tomakehurst.wiremock.client.WireMock.*

@RunWith(SpringRunner)
@SpringBootTest
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
@DirtiesContext
class HeatApplicationTests {

	@Test
	void "test running seed job"(){
		String job = """{
						"seedJobs":[{
							"seedCalls":[{
								"watchVariables":["albums.title"],
								"url":"http://localhost:8383/seed"
							}]
						}],
						"cacheJobs":[]
						}"""
		SpringApplication.run(HeatApplication.class, job)
		verify(getRequestedFor(urlEqualTo("/seed")))
	}

	@Test
	void "test running caching call"(){
		String job = """{
					"seedJobs":[{
						"seedCalls": [{
							"name":"seedCall",
							"watchVariables":["albums.title", "albums.track1", "albums.track2"],
							"url":"http://localhost:8383/seed"
						}]
					}],
					"cacheJobs": [{
						"urls":["http://localhost:8383/cachingcall/{seedCall.albums.title}/{seedCall.albums.track1}/{seedCall.albums.track2}/"],
						"body":""
					}]
				}"""
		SpringApplication.run(HeatApplication.class, job)
		verify(getRequestedFor(urlEqualTo("/cachingcall/andycapmusic/repitshawdy/whatdowedo/")))
	}
}