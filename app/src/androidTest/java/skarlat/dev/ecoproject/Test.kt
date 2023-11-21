package skarlat.dev.ecoproject

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith

class Test {
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testString() {
        val string = context.getString(R.string.app_name)
        assert(string == "")
    }

    @Test
    fun testString2() {
        val string = context.getString(R.string.register)
        assert(string == "Зарегестрироваться")
    }
}