/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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