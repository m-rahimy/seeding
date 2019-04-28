package ir.mrahimy.seeding.util

import ir.mrahimy.seeding.entity.People

object Init {
    fun generateSampleList(): List<People> {
        val list = listOf<People>(
                People("Reza", 2),
                People("Sadeq", 1),
                People("Mojtaba", 1),
                People("Asghar", 1),
                People("Elham", 2),
                People("Elahe", 2)
        )

        return list
    }
}