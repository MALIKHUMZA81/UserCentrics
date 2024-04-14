package com.usercentrics.interviewtest.data.mappers

import com.usercentrics.interviewtest.data.model.CentricsService
import com.usercentrics.sdk.v2.settings.data.UsercentricsService

object ServiceToTotalCostMapper {
    fun serviceToCost(
        userCentricsServices: List<UsercentricsService>,
        total: (Double) -> Unit,
    ) {
        var totalCost = 0.0
        userCentricsServices.forEach {
            val cost =
                calculateCost(
                    CentricsService(
                        it.dataProcessor ?: "",
                        it.dataCollectedList,
                    ),
                )
            println("${it.dataProcessor} = $cost")
            totalCost += cost
        }
        total(totalCost)
    }

    private fun calculateCost(service: CentricsService): Double {
        var cost = 0.0

        // Calculate base cost
        for (dataType in service.dataCollectedList) {
            cost +=
                when (dataType) {
                    APP_SETTINGS, DATE_AND_TIME_OF_VISIT -> 1
                    IP_ADDRESS, ADVERTISING_IDENTIFIER -> 2
                    USER_BEHAVIOUR, USER_AGENT -> 3
                    INTERNET_SERVICE_PROVIDER -> 4
                    BANK_DETAILS -> 5
                    PURCHASE_ACTIVITY -> 6
                    GEOGRAPHIC_LOCATION -> 7
                    APP_CRASHES -> -2
                    JAVASCRIPT_SUPPORT -> -1
                    else -> 0
                }
        }

        // Apply rules
        if (BANK_DETAILS in service.dataCollectedList &&
            PURCHASE_ACTIVITY in service.dataCollectedList &&
            CREDIT_CARD_SUPPORT in service.dataCollectedList
        ) {
            cost += (cost * 0.1) // Rule 1
        }

        if (SEARCH_TERMS in service.dataCollectedList &&
            GEOGRAPHIC_LOCATION in service.dataCollectedList &&
            NUMBER_OF_PAGE_VIEWS in service.dataCollectedList
        ) {
            cost += (cost * 0.27) // Rule 2
        }

        if (service.dataCollectedList.size <= 4) {
            cost -= (cost * 0.1) // Rule 3
        }

        return cost
    }

    private const val APP_SETTINGS = "App settings"
    private const val DATE_AND_TIME_OF_VISIT = "Date and time of visit"
    private const val IP_ADDRESS = "IP address"
    private const val ADVERTISING_IDENTIFIER = "Advertising identifier"
    private const val USER_BEHAVIOUR = "User behaviour"
    private const val USER_AGENT = "User agent"
    private const val INTERNET_SERVICE_PROVIDER = "Internet service provider"
    private const val BANK_DETAILS = "Bank details"
    private const val PURCHASE_ACTIVITY = "Purchase activity"
    private const val GEOGRAPHIC_LOCATION = "Geographic location"
    private const val APP_CRASHES = "App crashes"
    private const val SEARCH_TERMS = "Search terms"
    private const val NUMBER_OF_PAGE_VIEWS = "Number of page views"
    private const val JAVASCRIPT_SUPPORT = "JavaScript support"
    private const val CREDIT_CARD_SUPPORT = "Credit and debit card number"
}
