package name.brown.steve.dto

import groovy.transform.ToString

@ToString(includeFields = true)
class SeedCall {
    String url
    List<String> watchVariables
}
