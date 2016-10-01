package name.brown.steve.util
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedCallWatchResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class WatchVariableUtil {

    private static final Log log = LogFactory.getLog(this)

    /**
     * Return map representing all watch variables found with the seedCall name prepended to the key
     */
    static Map<String, List<String>> getWatchVariableResults(SeedCallContext seedResults){
        getWatchVariableResults(seedResults.contextData)
    }

    static Map<String, List<String>> getWatchVariableResults(Map<String, SeedCallWatchResult> contextData){
        contextData.collectEntries{ seedCallName, result ->
            result.watchVariableResult.collectEntries{ key, value ->
                [((String)"$seedCallName.$key") : value]
            }
        }
    }

    /**
     * Generate next set of watch variable values to plugin into url
     */
    static Map<String, String> expandValues(Map<String, List<String>> contextData, int count){
        contextData.collectEntries{ key, value ->
           [(key) : value[count] ?: value[0]]
        }
    }

    //convenience method
    static Map<String, String> getValuesFromResults(Map<String, SeedCallContext> contextData, int count){
        expandValues(getWatchVariableResults(contextData), count)
    }

    /**
     * Determines how many times a url should be called given the watch variables and how many permutations of the url
     * can exist for those watch variables
     */
    static int getLoopsForWatchVariables(Map<String, SeedCallWatchResult> contextData, String url){
        def watchVariables = getWatchVariableResults(contextData)
        if(watchVariables.keySet().empty || urlWithNoWatchVariable(watchVariables, url)){
            return 1 //call once if no watch variables to apply to url
        }
        log.trace "watch variables: $watchVariables"
        watchVariables.max {
             if (url.contains(it.key)){ //biggest list of watch variables if the url contains it
                 it.value.size()
             }else{
                 return 1 //don't count list of watch variables if the url doesn't contain it
             }
        }.value.size()
    }

    /**
     * Check if url contains any watch variables
     */
    static boolean urlWithNoWatchVariable(Map<String, List<String>> watchVariables, url){
        watchVariables.keySet().find{
            url.contains it
        } == 0
    }
}
