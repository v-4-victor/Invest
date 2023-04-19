package com.v4victor.invest

import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.BaseTestContext
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main

    override val viewClass: Class<*> = MainActivity::class.java

    val bottomNavigation = KBottomNavigationView { withId(R.id.bottomNavigationView) }
//    val searchTicket = KButton { withId(R.id.searchStock) }

//    val decrementButton = KButton { withId(R.id.decrement) }
//    val clearButton = KButton { withId(R.id.clear) }
//    val valueText = KTextView { withId(R.id.value) }
//
//    fun BaseTestContext.assertValue(value: Int) {
//        valueText.hasText(device.targetContext.getString(R.string.value_placeholder, value))
//    }
}
