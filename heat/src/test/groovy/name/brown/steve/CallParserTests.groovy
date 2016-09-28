package name.brown.steve

import name.brown.steve.dto.SeedCall
import name.brown.steve.dto.SeedCallWatchResult
import org.junit.Test

class CallParserTests {

    @Test
    void "parseResponse gets watch variables"(){
        String watchVariable = "albums.title"
        String albumTitle = "andycapmusic"
        String seedCallName = "seededAlbums"

        SeedCall seedCall = new SeedCall(
                name: seedCallName, url: "/seed", watchVariables: [watchVariable]
        )
        String json = """{
                "albums": [{
                    "title" : "$albumTitle",
                    "track1" : "repitshawdy",
                    "track2" : "whatdowedo"
                },
                {
                    "title" : "andycapmusic2",
                    "track1" : "goodluckhavefun",
                    "track2" : "keepmarching"
                }]
            }
        """

        SeedCallWatchResult response = CallParser.parseResponse(json, seedCall)
        assert response.watchVariableResult.get(watchVariable).contains(albumTitle)
    }
}
