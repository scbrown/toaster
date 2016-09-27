package name.brown.steve

import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedCallWatchResult

class TestUtil {
    static List<String> watchResult1 = ["andycapmusic", "andycapmusic2"]
    static List<String> watchResult2 = ["shoottherain", "woopintheclub"]
    static List<String> watchResult3 = ["dontworrybehappy", "bluemoon"]

    static SeedCallContext setupSeedCallContext() {
        def watchVariableResult = [ "albums.title" : watchResult1]
        def watchVariableResult2 = ["albums.track1": watchResult2]
        def watchVariableResult3 = ["albums.track2": watchResult3]


        def seedCallWatchResult = new SeedCallWatchResult(watchVariableResult: watchVariableResult)
        def seedCallWatchResult2 = new SeedCallWatchResult(watchVariableResult: watchVariableResult2)
        def seedCallWatchResult3 = new SeedCallWatchResult(watchVariableResult: watchVariableResult3)

        def seedCallContextData = new HashMap<SeedCall, SeedCallWatchResult>()

        seedCallContextData.put(new SeedCall(), seedCallWatchResult)
        seedCallContextData.put(new SeedCall(), seedCallWatchResult2)
        seedCallContextData.put(new SeedCall(), seedCallWatchResult3)

        def seedCallContext = new SeedCallContext(
                contextData: seedCallContextData
        )
        return seedCallContext
    }
}
