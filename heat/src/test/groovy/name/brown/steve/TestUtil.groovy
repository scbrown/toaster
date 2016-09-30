package name.brown.steve

import name.brown.steve.dto.SeedCallContext
import name.brown.steve.dto.SeedCallWatchResult

class TestUtil {
    static List<String> WATCH_RESULT1 = ["andycapmusic", "andycapmusic2"]
    static List<String> WATCH_RESULT2 = ["shoottherain", "woopintheclub"]
    static List<String> WATCH_RESULT3 = ["dontworrybehappy", "bluemoon"]

    static String SEED_CALL1 = "SEEDCALL1"
    static String SEED_CALL2 = "SEEDCALL2"
    static String SEED_CALL3 = "SEEDCALL3"

    static SeedCallContext setupSeedCallContext() {
        def watchVariableResult = ["albums.title" : WATCH_RESULT1]
        def watchVariableResult2 = ["albums.track1": WATCH_RESULT2]
        def watchVariableResult3 = ["albums.track2": WATCH_RESULT3]


        def seedCallWatchResult = new SeedCallWatchResult(watchVariableResult: watchVariableResult)
        def seedCallWatchResult2 = new SeedCallWatchResult(watchVariableResult: watchVariableResult2)
        def seedCallWatchResult3 = new SeedCallWatchResult(watchVariableResult: watchVariableResult3)

        def seedCallContextData = new HashMap<String, SeedCallWatchResult>()

        seedCallContextData.put(SEED_CALL1, seedCallWatchResult)
        seedCallContextData.put(SEED_CALL2, seedCallWatchResult2)
        seedCallContextData.put(SEED_CALL3, seedCallWatchResult3)

        def seedCallContext = new SeedCallContext(
                contextData: seedCallContextData
        )
        return seedCallContext
    }
}
