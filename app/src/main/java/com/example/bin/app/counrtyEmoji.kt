package com.example.bin.app

//  https://stackoverflow.com/questions/30494284/android-get-country-emoji-flag-using-locale
/**
 * This method is to change the country code like "us" into 🇺🇸
 * Stolen from https://stackoverflow.com/a/35849652/75579
 * 1. It first checks if the string consists of only 2 characters: ISO 3166-1 alpha-2 two-letter country codes (https://en.wikipedia.org/wiki/Regional_Indicator_Symbol).
 * 2. It then checks if both characters are alphabet
 * do nothing if it doesn't fulfil the 2 checks
 * caveat: if you enter an invalid 2 letter country code, say "XX", it will pass the 2 checks, and it will return unknown result
 */
fun String.toFlagEmoji(): String {
    // 1. It first checks if the string consists of only 2 characters: ISO 3166-1 alpha-2 two-letter country codes (https://en.wikipedia.org/wiki/Regional_Indicator_Symbol).
    if (this.length != 2) {
        return this
    }

    val countryCodeCaps = this.uppercase() // upper case is important because we are calculating offset
    val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

    // 2. It then checks if both characters are alphabet
    if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
        return this
    }

    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}
