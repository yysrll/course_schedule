
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.junit.Before

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class HomeActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun when_tap_add_course_then_addCourseActivity_displayed() {
        onView(allOf(withId(R.id.action_add), isDisplayed()))
            .perform(click())

        onView(allOf(withId(R.id.input_course_name), isDisplayed()))
            .check(matches(isDisplayed()))
    }
}
