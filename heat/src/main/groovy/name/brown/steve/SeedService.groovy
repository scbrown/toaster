package name.brown.steve

import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedCallWatchResult
import name.brown.steve.dto.SeedJob
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class SeedService {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    private RestTemplate restTemplate

    SeedCallContext getSeed(SeedJob seedJob){
        log.info "running job: $seedJob"
        Map contextData = new HashMap<SeedCall, SeedCallWatchResult>()
        seedJob.seedCalls.each{ seedCall ->
            log.info "calling $seedCall.url"
            String json = restTemplate.getForEntity(seedCall.url, String.class).body
            contextData.put(seedCall, CallParser.parseResponse(json, (SeedCall) seedCall))
            log.debug "current seed call context: $contextData"
        }
        return new SeedCallContext(contextData : contextData)
    }
}