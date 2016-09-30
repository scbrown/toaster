package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class SeedCallContext {
    Map<String, SeedCallWatchResult> contextData
}
