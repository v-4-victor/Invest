package com.v4victor.invest


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.search), withContentDescription("Search"), childAtPosition(
                    childAtPosition(
                        withId(R.id.materialToolbar), 1
                    ), 0
                ), isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

//        val appCompatEditText = onView(
//            allOf(
//                withId(R.id.editTextTextPersonName), childAtPosition(
//                    childAtPosition(
//                        withId(R.id.linear), 0
//                    ), 0
//                ), isDisplayed()
//            )
//        )
//        appCompatEditText.perform(replaceText("MSFT"), closeSoftKeyboard())
//
//        val appCompatEditText2 = onView(
//            allOf(
//                withId(R.id.editTextTextPersonName), withText("MSFT"), childAtPosition(
//                    childAtPosition(
//                        withId(R.id.linear), 0
//                    ), 0
//                ), isDisplayed()
//            )
//        )
//        appCompatEditText2.perform(pressImeActionButton())
//
//        val recyclerView = onView(
//            allOf(
//                withId(R.id.recyclerTickers), childAtPosition(
//                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 1
//                )
//            )
//        )
//        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
//
//        val textView = onView(
//            allOf(
//                withId(R.id.symbol),
//                withText("MSFT"),
//                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
//                isDisplayed()
//            )
//        )
//        textView.check(matches(withText("MSFT")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(
                    position
                )
            }
        }
    }
}
