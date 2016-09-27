package name.brown.steve.util

import name.brown.steve.dto.SeedCallContext

class WatchVariableUtil {

    /**
     * Return map representing all watch variables found
     */
    static Map<String, List<String>> getWatchVariableResults(SeedCallContext seedResults){
        seedResults.contextData.values().watchVariableResult.sum()
    }

    /**
     * Generate next set of watch variable values to plugin into url
     */
    static Map<String, String> expandValues(Map<String, List<String>> contextData, int count){
        def values = [:]
        contextData.each{ key, value ->
            if(value.size() == 1){
                values.put key, value[0]
            }else{
                values.put key, value[count]
            }
        }
        return values
    }
}
