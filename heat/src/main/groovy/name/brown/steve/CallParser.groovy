package name.brown.steve

import groovy.json.JsonSlurper
import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallWatchResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class CallParser {

    private static final Log log = LogFactory.getLog(this)

    static SeedCallWatchResult parseResponse(String json, SeedCall seedCall){
        def slurper
        SeedCallWatchResult response = new SeedCallWatchResult(
                watchVariableResult: new HashMap<String, ArrayList<String>>())
        List<String> found
        seedCall.watchVariables.each{ watchVariable ->
            log.debug "looking for variable: $watchVariable"
            slurper = new JsonSlurper().parseText(json)
            watchVariable.split("\\.").each{
                log.trace "slurper: $slurper it: $it"
                slurper = slurper."$it"
                log.trace "slurper after: $slurper"
            }
            found = slurper
            log.debug "parsing response for watch variable: $watchVariable found: $found"
            response.watchVariableResult.put(watchVariable, found)
        }
        return response
    }
}
