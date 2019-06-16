package nlab.practice.jetpack.repository

/**
 * @author Doohyun
 */
class LyricsRepository {

    fun getLyrics(): List<String> = ("Ah, ah, \n" +
            "We come from the land of the ice and snow, \n" +
            "From the midnight sun where the hot springs blow. \n" +
            "The hammer of the gods will drive our ships to new lands, \n" +
            "To fight the horde, singing and crying: Valhalla, I am coming! \n" +
            "\n" +
            "On we sweep with threshing oar, Our only goal will be the western shore. \n" +
            "\n" +
            "Ah, ah, \n" +
            "We come from the land of the ice and snow, \n" +
            "From the midnight sun where the hot springs blow. \n" +
            "How soft your fields so green, can whisper tales of gore, \n" +
            "Of how we calmed the tides of war. We are your overlords. \n" +
            "\n" +
            "On we sweep with threshing oar, Our only goal will be the western shore. \n" +
            "\n" +
            "So now you'd better stop and rebuild all your ruins, \n" +
            "For peace and trust can win the day despite of all your losing.\n")
            .split("\n")
            .filter { it.isNotEmpty() }


}