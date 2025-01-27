package com.example.wordleapp

import android.content.Context

data class WordleState(
    var word: MutableList<String>,
    val guesses: List<MutableList<String>> = List(6) { MutableList(5) { "" } },
    val currentGuessIndex: Int = 0
)

