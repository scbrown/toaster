package name.brown.steve

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.deployer.spi.task.LaunchState
import org.springframework.cloud.stream.annotation.Bindings
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.cloud.task.launcher.TaskLaunchRequest
import org.springframework.cloud.task.launcher.TaskLauncherSink
import org.springframework.context.ApplicationContext
import org.springframework.messaging.support.GenericMessage
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class ElecApplicationTests {

	@Autowired
	ApplicationContext context

	@Autowired
	@Bindings(TaskLauncherSink.class)
	private Sink sink

	@Test
	void contextLoads() {
	}

	@Test
	public void testLaunch() {
		assert this.sink.input()

		TaskSinkConfiguration.TestTaskLauncher testTaskLauncher =
				(TaskSinkConfiguration.TestTaskLauncher) context.getBean(TaskSinkConfiguration.TestTaskLauncher.class);

		Map<String, String> properties = new HashMap()
		properties.put("server.port", "0")
		TaskLaunchRequest request = new TaskLaunchRequest("maven://name.brown.steve:heat:jar:0.0.1-SNAPSHOT",
				null, properties, null)
		GenericMessage<TaskLaunchRequest> message = new GenericMessage<TaskLaunchRequest>(request)
		this.sink.input().send(message)
		assert LaunchState.complete == testTaskLauncher.status("TESTSTATUS").getState()
	}
}
