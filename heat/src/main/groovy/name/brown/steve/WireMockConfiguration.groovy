package name.brown.steve

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("wiremock")
class WireMockConfiguration extends org.springframework.cloud.contract.wiremock.WireMockConfiguration{
}
