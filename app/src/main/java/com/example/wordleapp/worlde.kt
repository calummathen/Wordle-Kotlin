package com.example.wordleapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun WordleScreen() {
    val boxModifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)
        .navigationBarsPadding()
        .systemBarsPadding()
    Column(boxModifier) {
        WordleHeader()
        WordleBody()
    }
}

@Composable
fun WordleHeader() {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, bottom = 20.dp)){
        Text(
            text = "WORDLE",
            fontSize = 40.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordleBody(wordleViewModel: WordleViewModel = viewModel()) {
    val wordleState = wordleViewModel.currentState

    val boxColours0 = remember { mutableStateListOf(Color.White, Color.White, Color.White, Color.White, Color.White) }
    val boxColours1 = remember { mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray) }
    val boxColours2 = remember { mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray) }
    val boxColours3 = remember { mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray) }
    val boxColours4 = remember { mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray) }
    val boxColours5 = remember { mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray) }

    val submitEnabled = remember { mutableStateOf(true) }

    Column (modifier = Modifier.fillMaxSize()){
//            val attempts = remember { MutableList(6) { mutableStateListOf("", "", "", "", "") } }
            val attempts = remember { mutableStateListOf(
                mutableStateListOf("", "", "", "", ""),
                mutableStateListOf("", "", "", "", ""),
                mutableStateListOf("", "", "", "", ""),
                mutableStateListOf("", "", "", "", ""),
                mutableStateListOf("", "", "", "", ""),
                mutableStateListOf("", "", "", "", "")
            )}
            val boxColoursList = mutableListOf(boxColours0, boxColours1, boxColours2, boxColours3, boxColours4, boxColours5)
            val textEnabledList = remember {mutableStateListOf(true, false, false, false, false, false)}
            val focusRequesters = remember { List(5) { FocusRequester() } }

            repeat(6){ rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val wordAttempt = attempts[rowIndex]
                    val boxColours = boxColoursList[rowIndex]
                    val textEnabled = textEnabledList[rowIndex]



                    repeat(5) { index ->

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(boxColours[index])
                                .height(40.dp)
                                .aspectRatio(1f)
                        ) {
                            TextField(
                                value = wordAttempt[index].uppercase(),
                                enabled = textEnabled,
                                onValueChange = {
                                    if (it.length <= 1) wordAttempt[index] = it.uppercase()
                                    if (it.length == 1 && index < 4) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                                },
                                modifier = Modifier.fillMaxSize().align(Alignment.Center).focusRequester(focusRequesters[index]),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = if (index < 4) ImeAction.Next else ImeAction.Done),
//                                keyboardActions = KeyboardActions(
//                                    onDone = {
//                                        handleSubmit()
//                                    }
//                                ),
                                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    errorContainerColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
//            val newBoxColours: MutableList<Color> = checkWord(wordleState.word, wordAttempt1)
//            boxColours = newBoxColours as SnapshotStateList<Color>

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                var buttonIndex = 0
                val context = LocalContext.current

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = submitEnabled.value,
                    onClick = {
                    when(wordleState.currentGuessIndex){
                       0 -> buttonIndex = 0
                        1 -> buttonIndex = 1
                        2 -> buttonIndex = 2
                        3 -> buttonIndex = 3
                        4 -> buttonIndex = 4
                        5 -> buttonIndex = 5
                        else -> buttonIndex = 6
                    }
                    boxColoursList[buttonIndex].clear() // Clear the existing list
                    boxColoursList[buttonIndex].addAll((checkWord(wordleState.word, attempts[buttonIndex])))

                        if (boxColoursList[buttonIndex].all { it == Color.Green }) {
                            Toast.makeText(context, "CORRECT!", Toast.LENGTH_LONG).show();
                            textEnabledList[buttonIndex] = false
                            for (i in 5 downTo buttonIndex + 1) {
                                boxColoursList[i].clear()
                                boxColoursList[i].addAll(mutableStateListOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray))
                            }
                            buttonIndex = 6
                            submitEnabled.value = false

                        }
                        if(buttonIndex < 5){
                        wordleViewModel.nextGuess(attempts[buttonIndex])
                        textEnabledList[buttonIndex] = false
                        textEnabledList[buttonIndex + 1] = true
                        boxColoursList[buttonIndex + 1].clear()
                        boxColoursList[buttonIndex + 1].addAll(mutableStateListOf(Color.White, Color.White, Color.White, Color.White, Color.White))
                    } else if(buttonIndex == 5) {
                        wordleViewModel.nextGuess(attempts[buttonIndex])
                        textEnabledList[buttonIndex] = false
                    }
                }){
                    Text(text = "Submit", fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Button(onClick = {

                    for(i in attempts.indices) {
                        attempts[i].clear()
                        attempts[i].addAll(mutableStateListOf("","","","",""))
                    }

                    for (i in boxColoursList.indices) {
                        boxColoursList[i].clear()
                        boxColoursList[i].addAll(mutableStateListOf(Color.White, Color.White, Color.White, Color.White, Color.White))
                    }

                    for (i in textEnabledList.indices) {
                        textEnabledList[i] = false
                    }
                    textEnabledList[0] = true
                    wordleViewModel.resetQuiz()
                    submitEnabled.value = true
                }) {
                    Text(text = "Reset", fontSize = 20.sp)
                }
            }


//        Text(text = wordAttempt1.joinToString(""))
//        Text(text = wordleState.word.joinToString(""))
        println(wordleState.word.joinToString(""))
    }

}

fun checkWord(actualWord: List<String>, wordToCheck: MutableList<String>): MutableList<Color> {
    val colours = wordToCheck.mapIndexed{ index, letter ->
        when (letter) {
            actualWord[index] -> Color.Green
            in actualWord -> Color.Yellow
            else -> Color.DarkGray
        }
    }
    val outputColours = colours.toMutableList()
    return outputColours
}