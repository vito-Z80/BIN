package com.example.bin.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bin.cardData


// TODO добавить звонилку   https://mikimints.medium.com/building-a-modern-call-recorder-with-kotlin-android-jetpack-chapter-1-c0aab354facd
//  https://www.geeksforgeeks.org/android-open-dialer-through-intent-using-jetpack-compose/
//  Добавить гугл карты:    https://betterprogramming.pub/exploring-google-map-compose-library-for-android-af2a784f9508
//  https://developers.google.com/maps
//  https://cloud.google.com/blog/products/maps-platform/compose-maps-sdk-android-now-available

private const val UNDEFINED = "Undefined"
private const val YES = "Yes"
private const val NO = "No"

@Composable
fun BinInfo() {
    if (cardData.value == null) return
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4f.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(text = "Card information", style = MaterialTheme.typography.h1)
            Divider99()
            Scheme()
            Type()
            Bank(context = context)
            Brand()
            Prepaid()
            CardNumber()
            Country()
            CountryLocation(context = context)
        }
    }
}

@Composable
private fun Divider99() {
    Divider(modifier = Modifier.fillMaxWidth(0.99f), color = MaterialTheme.colors.primary)
}

@Composable
private fun TextHalfScreen(text: String, style: TextStyle = MaterialTheme.typography.h2) {
    Text(
        text = text, style = style, modifier = Modifier
            .fillMaxWidth(0.7f)
            .horizontalScroll(
                ScrollState(0)
            ),
        textAlign = TextAlign.End
    )
}

@Composable
private fun Bank(context: Context) {
    if (cardData.value?.bank != null) {
        val telIntent = Intent(Intent.ACTION_DIAL)
        val bank = cardData.value?.bank
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Bank:", style = MaterialTheme.typography.h4)
            TextHalfScreen(text = bank?.name ?: UNDEFINED, style = MaterialTheme.typography.h2)
        }
        if (bank?.city != null) {
            Divider99()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "City:", style = MaterialTheme.typography.h4)
                TextHalfScreen(text = bank.city, style = MaterialTheme.typography.h2)
            }
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            cardData.value?.bank?.phone?.phoneCutter()?.forEach { tel ->
                Text(text = tel, modifier = Modifier.clickable {
                    telIntent.data = Uri.parse("tel:$tel")
                    context.startActivity(telIntent)
                }, style = MaterialTheme.typography.h5)
            }
        }
        Divider99()
    }
}

@Composable
private fun CardNumber() {
    if (cardData.value?.number != null) {
        val number = cardData.value?.number
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Number length:", style = MaterialTheme.typography.h4)
            TextHalfScreen(
                text = "${number?.length ?: UNDEFINED}",
                style = MaterialTheme.typography.h2
            )
        }
        Divider99()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Card LUHN:", style = MaterialTheme.typography.h4)
            TextHalfScreen(
                text = if (number?.luhn == true) YES else NO,
                style = MaterialTheme.typography.h2
            )
        }
        Divider99()
    }
}

@Composable
private fun Prepaid() {
    if (cardData.value?.prepaid != null) {
        val prepaid = cardData.value?.prepaid!!
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Prepaid:", style = MaterialTheme.typography.h4)
            TextHalfScreen(text = if (prepaid) YES else NO, style = MaterialTheme.typography.h2)
        }
        Divider99()
    }
}


@Composable
private fun Brand() {
    if (cardData.value?.brand != null) {
        val brand = cardData.value?.brand
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Brand:", style = MaterialTheme.typography.h4)
            TextHalfScreen(text = "$brand", style = MaterialTheme.typography.h2)
        }
        Divider99()
    }
}

@Composable
private fun Type() {
    if (cardData.value?.type != null) {
        val type = cardData.value?.type
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Type:", style = MaterialTheme.typography.h4)
            TextHalfScreen(text = "$type", style = MaterialTheme.typography.h2)
        }
        Divider99()
    }
}

@Composable
private fun Scheme() {
    if (cardData.value?.scheme != null) {
        val scheme = cardData.value?.scheme
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Scheme/Network:", style = MaterialTheme.typography.h4)
            TextHalfScreen(text = "$scheme", style = MaterialTheme.typography.h2)
        }
        Divider99()
    }
}

@Composable
private fun Country() {
    if (cardData.value?.country?.name != null) {
        val country = cardData.value?.country
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Country:", style = MaterialTheme.typography.h4)
            TextHalfScreen(
                text = "${country?.emoji?.toFlagEmoji()} ${country?.name}",
                style = MaterialTheme.typography.h2
            )
        }
        Divider99()
    }
}

@SuppressLint("QueryPermissionsNeeded")
@Composable
private fun CountryLocation(context: Context) {
    if (cardData.value?.country?.latitude != null && cardData.value?.country?.longitude != null) {
        val latitude = cardData.value?.country?.latitude!!.toFloat()
        val longitude = cardData.value?.country?.longitude!!.toFloat()
        val gmIntent = Intent()
        gmIntent.`package` = "com.google.android.apps.maps"
        if (gmIntent.resolveActivity(context.packageManager) == null) {
            Toast.makeText(context, "Google Maps absent on your device.", Toast.LENGTH_SHORT).show()
            return
        }
        gmIntent.action = Intent.ACTION_VIEW
        gmIntent.data = Uri.parse("google.streetview:cbll=$latitude,$longitude")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    context.startActivity(gmIntent)
                }, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Location:", style = MaterialTheme.typography.h4)
            Text(text = "$latitude, $longitude", style = MaterialTheme.typography.h5)
        }
        Divider99()
    }
}