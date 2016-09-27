package name.brown.steve
import groovy.json.JsonSlurper
import name.brown.steve.dto.HeatJob
import name.brown.steve.dto.SeedCallContext
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner

public class HeatTask implements CommandLineRunner{

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    SeedService seedService

    @Autowired
    CacheRequestService cacheRequestService

    public void run(String[] jobs) throws Exception {
        log.info "jobs: $jobs"
        List<HeatJob> heatJobs = new ArrayList<>()
        jobs.each { job ->
             heatJobs.add((HeatJob) new JsonSlurper().parseText(job))
        }
        log.debug "running heatJobs: $heatJobs"
        heatJobs.each{ heatJob ->
            log.debug "running heatJob: $heatJob"
            List<SeedCallContext> seedCallContexts = new ArrayList<>()
            heatJob.seedJobs.each{seedJob ->
                log.debug "running seedJob: $seedJob"
                seedCallContexts.add(seedService.getSeed(seedJob))
            }
            log.info "seedCallContexts: $seedCallContexts"

            heatJob.cacheJobs.each{ cacheJob ->
                log.info "running cacheJob: $cacheJob"
                cacheRequestService.doCacheCall(cacheJob, collapsSeedCallContexts(seedCallContexts) )
            }
        }
    }

    private static SeedCallContext collapsSeedCallContexts(List<SeedCallContext> seedCallContexts){
        def contextData = seedCallContexts.contextData.sum()
        log.trace "collapsed contextData: $contextData"
        return new SeedCallContext(contextData: contextData)
    }
}
