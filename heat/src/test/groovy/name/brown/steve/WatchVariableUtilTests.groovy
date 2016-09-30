package name.brown.steve

import name.brown.steve.util.WatchVariableUtil
import org.junit.Test

class WatchVariableUtilTests {
    String testWatchVariable = "albums.title"
    def testWatchResult = TestUtil.WATCH_RESULT1[0]

    @Test
    void "test getWatchVariableResults"(){
        def response = WatchVariableUtil.getWatchVariableResults(TestUtil.setupSeedCallContext())
        assert response.get((String)"$TestUtil.SEED_CALL1.$testWatchVariable")[0] == testWatchResult
    }

    @Test
    void "test expandValues"(){
        def callCount = 0
        def seedResponses = WatchVariableUtil.getWatchVariableResults(TestUtil.setupSeedCallContext())
        def response = WatchVariableUtil.expandValues(seedResponses, callCount)
        assert response.get((String)"$TestUtil.SEED_CALL1.$testWatchVariable") == testWatchResult
    }
}
