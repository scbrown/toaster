package name.brown.steve

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8383, stubs="classpath:/stubs/")
class HeatApplicationTests {
    
	@Rule
	public OutputCapture outputCapture = new OutputCapture()

	@Test
	void "test running basic job"(){
		String job = """{"seedCalls":[{
				"watchVariables":["albums.title"],
				"url":"http://localhost:8383/seed"}]}"""
		String expected = "andycapmusic"
		SpringApplication.run(HeatApplication.class, job)
		String output = this.outputCapture.toString()

		assert output.contains(expected)
	}
}