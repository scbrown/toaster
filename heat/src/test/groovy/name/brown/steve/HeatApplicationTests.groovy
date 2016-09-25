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
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	void "test task execution"() {
	    String expected = "oh hai!"
	    SpringApplication.run(HeatApplication.class, "")
	    String output = this.outputCapture.toString()
	    
	    assert output.contains(expected)
	}
}