package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class SeedCallWatchResult {
    Map<String, List<String>> watchVariableResult
}
