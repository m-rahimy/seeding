package ir.mrahimy.seeding

import ir.mrahimy.seeding.util.Init
import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class DatabaseUnitTest {

    @Test
    fun ensureValidSampleList() {
        val list = Init.generateSampleList()
        val listForSeed1 = list.filter { it.seed == 1 }
        val listForSeed2 = list.filter { it.seed == 2 }

        assert(listForSeed1.size == listForSeed2.size)
    }
}