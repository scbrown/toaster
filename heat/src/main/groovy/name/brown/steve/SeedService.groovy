package name.brown.steve
import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedCallWatchResult
import name.brown.steve.util.WatchVariableUtil
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

    SeedCallContext getSeed(def seedJob){
        log.info "running job: $seedJob"
        Map contextData = new HashMap<String, SeedCallWatchResult>()
        seedJob.seedCalls.each{ seedCall ->
            log.info "calling $seedCall.url"
            WatchVariableUtil.getLoopsForWatchVariables(contextData, seedCall.url).times{ count ->
                //TODO: how do I know these iterations will be used?
                String json = restTemplate.getForEntity(seedCall.url, String,
                        WatchVariableUtil.getValuesFromResults(contextData, count)).body
                contextData.put(seedCall.name, CallParser.parseResponse(json, (SeedCall) seedCall))
                log.debug "current seed call context: $contextData"
            }
        }
        return new SeedCallContext(contextData : contextData)
    }
}
