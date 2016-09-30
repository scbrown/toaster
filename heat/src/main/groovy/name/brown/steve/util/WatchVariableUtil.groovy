package name.brown.steve.util

import name.brown.steve.dto.SeedCallContext

class WatchVariableUtil {

    /**
     * Return map representing all watch variables found with the seedCall name prepended to the key
     */
    static Map<String, List<String>> getWatchVariableResults(SeedCallContext seedResults){
        seedResults.contextData.collectEntries{ seedCallName, result ->
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
}
