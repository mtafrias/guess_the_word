/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtafrias.android.guesstheword.screens.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    val word = MutableLiveData<String>()

    var score = MutableLiveData<Int>()

    var eventGameFinish = MutableLiveData<Boolean>()

    private lateinit var wordList: MutableList<String>

    private fun resetList() {
        word.value = ""
        score.value = 0

        wordList = mutableListOf(
            "rainha",
            "hospital",
            "basquetebol",
            "gato",
            "mudança",
            "lesma",
            "sopa",
            "calendário",
            "tristeza",
            "mesa",
            "guitarra",
            "casa",
            "rodovia",
            "zebra",
            "gelatina",
            "carro",
            "corvo",
            "negociar",
            "saco",
            "rolar",
            "bolha"
        )
        wordList.shuffle()

        nextWord()
    }

    init {
        resetList()
    }

    fun onSkip() {
        score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        score.value = (score.value)?.plus(1)
        nextWord()
    }

    private fun onGameFinish() {
        eventGameFinish.value = true
    }

    fun onGameFinishComplete() {
        eventGameFinish.value = false
        resetList()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            onGameFinish()
        } else {
            word.value = wordList.removeAt(0)
        }
    }
}
