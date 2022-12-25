@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.bin.app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bin.*

private val regNoDigits = Regex("[^0-9]")

@Composable
fun Content(context: Context) {

    val isKeyboardOpen = keyboardAsState()

    val isDone = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isDone.value) {
        if (isDone.value) {
            isDone.value = false
            request(bin.value, context)
            addBinValue(context, bin.value)
            bin.value = ""
        }

    }

    Column(
        modifier = Modifier

            .fillMaxWidth()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bank Identification Number",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(4f.dp))
        TextField(
            value = bin.value,
            onValueChange = {
                bin.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (bin.value.trim().contains(regNoDigits) || bin.value.trim()
                            .isEmpty() || bin.value.trim().length > 16
                    ) {
                        Toast.makeText(context, "Invalid data entered", Toast.LENGTH_SHORT).show()
                    } else {
                        isDone.value = true
                        keyboardController?.hide()
                    }
                }),
            isError = bin.value.trim().contains(regNoDigits) || bin.value.trim()
                .isEmpty() || bin.value.trim().length > 16
        )

        Text(
            text = "Enter the first digits of a card number (BIN/IIN)",
            style = MaterialTheme.typography.h6
        )

        if (isKeyboardOpen.value == Keyboard.Opened) {
            BinsHistory(context = context, keyboardController, isDone)
        } else {
            BinInfo()
        }
    }

    if (requestFalse.value) {
        requestFalse.value = false
        Toast.makeText(
            context,
            "Invalid data entered or card does not exist.\nTry again.",
            Toast.LENGTH_LONG
        )
            .show()
    }

}






