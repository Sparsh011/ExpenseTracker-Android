package com.sparshchadha.expensetracker.feature.auth.domain.usecase

import com.sparshchadha.expensetracker.feature.auth.domain.exceptions.InvalidPhoneException

class ValidateAndSeparatePhoneAndCCUseCase {
    operator fun invoke(phoneNumberWithCountryCode: String, delimiter: Char): Pair<String, String> {

        if (!phoneNumberWithCountryCode.startsWith("+")) throw InvalidPhoneException()
        if (phoneNumberWithCountryCode.isBlank() || phoneNumberWithCountryCode.length < 6 || phoneNumberWithCountryCode.length > 15) throw InvalidPhoneException()

        val delimiterIndex = phoneNumberWithCountryCode.indexOf(delimiter)

        if (delimiterIndex + 1 >= phoneNumberWithCountryCode.length) {
            throw InvalidPhoneException()
        }

        val countryCode = phoneNumberWithCountryCode.substring(0, delimiterIndex)
        val phone = phoneNumberWithCountryCode.substring(
            delimiterIndex + 1,
            phoneNumberWithCountryCode.length
        )

        if (phone.isBlank() || phone.length < 5 || phone.length > 15) throw InvalidPhoneException()
        if (!countryCode.startsWith('+') || countryCode.trim().length < 2) throw InvalidPhoneException()

        return Pair(countryCode, phone)
    }
}