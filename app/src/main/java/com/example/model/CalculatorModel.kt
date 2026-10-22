package com.example.model

enum class CalculatorMode(val titleBn: String, val titleEn: String) {
    STANDARD("সাধারণ ক্যালকুলেটর", "Standard"),
    PERCENTAGE("শতকরা ও ডিসকাউন্ট", "Percentage & Discount"),
    PROFIT_LOSS("লাভ-ক্ষতি ও ব্যবসা", "Profit & Loss"),
    LAND_CONVERTER("ভূমির পরিমাপ ও রূপান্তর", "Land Units")
}

data class ProfitLossResult(
    val costPrice: Double,
    val sellingPrice: Double,
    val isProfit: Boolean,
    val differenceAmount: Double,
    val percentage: Double
)

data class LandUnitResult(
    val shotokDecimal: Double, // শতক / ডেসিমাল
    val katha: Double,         // কাঠা (১ কাঠা = ১.৬৫ শতক)
    val bigha: Double,         // বিঘা (১ বিঘা = ২০ কাঠা = ৩৩ শতক)
    val acre: Double,          // একর (১ একর = ১০০ শতক)
    val sqFeet: Double,        // বর্গফুট (১ শতক = ৪৩৫.৬ বর্গফুট)
    val sqMeters: Double       // বর্গমিটার (১ শতক = ৪০.৪৬ বর্গমিটার)
)
