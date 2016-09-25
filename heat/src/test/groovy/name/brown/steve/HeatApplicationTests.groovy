package name.brown.steve

import org.junit.Rule
import org.junit.Test

import org.springframework.boot.SpringApplication
import org.springframework.boot.test.rule.OutputCapture

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