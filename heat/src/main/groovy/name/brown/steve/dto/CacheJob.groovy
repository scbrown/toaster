package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class CacheJob {
    List<String> urls
    String body
}
