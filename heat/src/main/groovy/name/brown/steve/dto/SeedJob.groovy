package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class SeedJob {
    List<SeedCall> seedCalls
}