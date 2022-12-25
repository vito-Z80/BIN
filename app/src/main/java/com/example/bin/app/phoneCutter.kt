package com.example.bin.app

//  https://planetcalc.ru/4223/
private val regPhoneNumber =
    Regex("(\\d?)[\\s-()]?(\\d{3})[\\s-()]?(\\d{3})[\\s-()]?(\\d{2})[\\s-()]?(\\d{2})")

fun String.phoneCutter() = regPhoneNumber.findAll(this).map { it.value }