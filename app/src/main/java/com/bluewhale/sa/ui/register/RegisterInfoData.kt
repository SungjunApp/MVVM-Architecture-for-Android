package com.bluewhale.sa.ui.register


data class RegisterInfoData(
    var name: String,
    var personalCode1: String,
    var personalCode2: String,
    var phone: String,
    var provider: MobileProvider
) {

    fun isNameFull(): Boolean {
        return name.isNotEmpty()
    }

    fun isPersonalCode1Full(): Boolean {
        return personalCode1.isNotEmpty() && personalCode1.length == 6
    }

    fun isPersonalCode2Full(): Boolean {
        return personalCode2.isNotEmpty() && personalCode2.length == 1
    }

    fun isPhoneFull(): Boolean {
        return phone.isNotEmpty() && phone.length == 11
    }

    fun isProviderSelected(): Boolean {
        return provider != MobileProvider.UNSELECTED
    }

    fun isInfoFilledUp(): Boolean {
        return isNameFull() && isPersonalCode1Full() && isPersonalCode2Full() && isPhoneFull() && isProviderSelected()
    }

    enum class MobileProvider(val providerCode: Int) {
        UNSELECTED(-1),
        SKT(0),
        KT(1),
        LG(2),
        SKT_SUB(3),
        KT_SUB(4),
        LG_SUB(5)
    }
}