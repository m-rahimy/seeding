package ir.mrahimy.seeding

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import ir.mrahimy.seeding.db.DbManager
import ir.mrahimy.seeding.util.Init

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    @Test
    fun ensureDbContainsFullSampleList() {
        val context = InstrumentationRegistry.getTargetContext()
        val db = DbManager(context)
        db.clearPeople()
        val list = Init.generateSampleList()
        list.forEach {
            db.putPeople(it)
        }

        val dbList = db.getPeople()

        assert(dbList.size == list.size)
    }
}
