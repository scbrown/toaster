package name.brown.steve

import name.brown.steve.util.WatchVariableUtil
import org.junit.Test

class WatchVariableUtilTests {
    def testWatchVariable = "albums.title"
    def testWatchResult = TestUtil.watchResult1[0]

    @Test
    void "test flattenCallContext"(){
        def response = WatchVariableUtil.getWatchVariableResults(TestUtil.setupSeedCallContext())
        assert response.get(testWatchVariable)[0] == testWatchResult
    }

    @Test
    void "test expandValues"(){
        def callCount = 0
        def seedResponses = WatchVariableUtil.getWatchVariableResults(TestUtil.setupSeedCallContext())
        def response = WatchVariableUtil.expandValues(seedResponses, callCount)
        assert response.get(testWatchVariable) == testWatchResult
    }
}
