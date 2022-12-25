@file:OptIn(ExperimentalUnitApi::class, ExperimentalComposeUiApi::class)

package com.example.bin.app

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.example.bin.*


@Composable
fun BinsHistory(
    context: Context,
    keyboardController: SoftwareKeyboardController?,
    isDone: MutableState<Boolean>
) {

    val state = rememberLazyListState()

    LaunchedEffect(true) {
        valuesStorage.value = readBinValues(context)
        state.animateScrollToItem(index = 0)

    }

    LaunchedEffect(bin.value) {
        valuesStorage.value = valuesStorage.value?.sorted()?.sortedByDescending { it.startsWith(bin.value) }
        state.animateScrollToItem(index = 0)
    }
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = state) {

        items(valuesStorage.value ?: emptyList()) {

            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4f.dp)
                    .fillMaxWidth()
                    .clickable {
                        keyboardController?.hide()
                        bin.value = it
                        isDone.value = true
                    },
            style = MaterialTheme.typography.h4)
            Divider(modifier = Modifier.fillMaxWidth(0.7f))
        }
    }
}