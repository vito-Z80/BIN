package com.example.bin.app

import com.google.gson.Gson

val gson = Gson()


data class BinData(
    val bank: Bank? = null,
    val brand: String? = null,
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean? = null,
    val scheme: String? = null,
    val type: String? = null
) {
    data class Bank(
        val city: String? = null,
        val name: String? = null,
        val phone: String? = null,
        val url: String? = null
    )

    data class Country(
        val alpha2: String? = null,
        val currency: String? = null,
        val emoji: String? = null,
        val latitude: Int? = null,
        val longitude: Int? = null,
        val name: String? = null,
        val numeric: String? = null
    )

    data class Number(
        val length: Int? = null,
        val luhn: Boolean? = null
    )
}