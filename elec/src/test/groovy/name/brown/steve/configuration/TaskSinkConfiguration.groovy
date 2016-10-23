package name.brown.steve

import org.springframework.cloud.deployer.spi.core.AppDeploymentRequest
import org.springframework.cloud.deployer.spi.task.LaunchState
import org.springframework.cloud.deployer.spi.task.TaskLauncher
import org.springframework.cloud.deployer.spi.task.TaskStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskSinkConfiguration{

    @Bean
    public TaskLauncher taskLauncher() {
        return new TestTaskLauncher()
    }

    public class TestTaskLauncher implements TaskLauncher {

        public static final String LAUNCH_ID = "TEST_LAUNCH_ID"

        private LaunchState state = LaunchState.unknown

        @Override
        public String launch(AppDeploymentRequest request) {
            state = LaunchState.complete
            return null
        }

        @Override
        public void cancel(String id) {

        }

        @Override
        public TaskStatus status(String id) {
            String taskLaunchId = LAUNCH_ID
            TaskStatus taskStatus = new TaskStatus(taskLaunchId, state, null)
            return taskStatus
        }
    }
}
