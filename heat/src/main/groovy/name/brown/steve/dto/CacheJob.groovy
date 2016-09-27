package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class CacheJob {
    String url
    String body
}
