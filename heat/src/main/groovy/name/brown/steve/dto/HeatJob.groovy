package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class HeatJob {
    List<SeedJob> seedJobs
    List<CacheJob> cacheJobs
}
