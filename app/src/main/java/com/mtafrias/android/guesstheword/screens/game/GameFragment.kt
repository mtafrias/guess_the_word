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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        viewModel.word.observe(viewLifecycleOwner) {
            binding.wordText.text = it
        }

        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreText.text = it?.toString()
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) {
            if (it) {
                gameFinished()
            }
            binding.correctButton.isEnabled = !it
            binding.skipButton.isEnabled = !it
        }

        return binding.root
    }

    private fun onSkip() = viewModel.onSkip()

    private fun onCorrect() = viewModel.onCorrect()

    private fun onEndGame() = gameFinished()

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value ?: 0
        viewModel.onGameFinishComplete()
        NavHostFragment.findNavController(this).navigate(action)
    }
}
