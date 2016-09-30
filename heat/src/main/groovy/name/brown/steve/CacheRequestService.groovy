package name.brown.steve

import name.brown.steve.dto.SeedCallContext
import name.brown.steve.util.WatchVariableUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class CacheRequestService {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    RestTemplate restTemplate

    /**
     * Call caching url for every set of watch variables found
     */
    void doCacheCall(def job, SeedCallContext seedCallContext){
        log.info "using job urls: $job.urls"
        Map<String, List<String>> seedResponses = WatchVariableUtil.getWatchVariableResults(seedCallContext)
        job.urls.each{ url ->
            seedResponses.max {
                it.value.size() //biggest list of values
            }.value.size().times{ count ->
                log.info "working with seedResponses: ${seedResponses} count: $count"
                def expandedValues = WatchVariableUtil.expandValues(seedResponses, count)
                log.info "expanded values: ${expandedValues.toMapString()} ${expandedValues.keySet()}"

                restTemplate.getForObject((String)url, Object, expandedValues)
            }
        }
    }
}
