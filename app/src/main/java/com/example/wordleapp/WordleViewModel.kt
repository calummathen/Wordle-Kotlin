package com.example.wordleapp

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.getString
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel



//class WordleViewModel(context: Context): ViewModel() {
class WordleViewModel(application: Application) : AndroidViewModel(application) {

    private val _wordleState = WordleState(
        word = getRandomWord(),
        guesses = List(6) { kotlin.collections.MutableList(5) { "" } },
    )
    var currentState = _wordleState
        private set

    fun getRandomWord(): MutableList<String>{
//        val wordList = mutableListOf("FLASK", "ZEBRA", "AUDIO", "CREST", "TABLE")
        val wordList: MutableList<String> = getApplication<Application>().resources.getStringArray(R.array.wordList).toMutableList()
//        val wordList = context.resources.getStringArray(R.array.wordList).toMutableList()
        val randomElement = wordList.asSequence().shuffled().find { true }?.uppercase()
        val wordAsList = randomElement?.map { it.toString() }
        return wordAsList!!.toMutableList()
    }

    fun nextGuess(currentGuess: MutableList<String>) {
        if (currentState.currentGuessIndex < 6) {
            // Update the specific guess at the current index
            val updatedGuesses = currentState.guesses.toMutableList().apply {
                this[currentState.currentGuessIndex] = currentGuess
            }

            // Update the state with the new guesses and increment the guess index
            currentState = currentState.copy(
                guesses = updatedGuesses,
                currentGuessIndex = currentState.currentGuessIndex + 1
            )
        }
    }

    fun resetQuiz(){
        currentState = currentState.copy(currentGuessIndex = 0,
            guesses = List(6) { kotlin.collections.MutableList(5) { "" } },
            word = getRandomWord()
        )
    }
}