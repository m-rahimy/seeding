package ir.mrahimy.seeding.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ir.mrahimy.seeding.entity.People
import ir.mrahimy.seeding.entity.Team

class DbManager(val context: Context) :
        SQLiteOpenHelper(context, "name", null, 1) {
    companion object {
        val TABLE_PEOPLE = "PEOPLE"
        val PEOPLE_NAME = "name"
        val PEOPLE_SEED = "seed"


        val TABLE_TEAM = "team"
        val TEAM_NAME = "team_name"
        val TEAM_A = "person_a"
        val TEAM_B = "person_b"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        createPeopleTable(db)
        createTeamTable(db)
    }

    private fun createPeopleTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_PEOPLE " +
                "($PEOPLE_NAME text primary key " +
                ", $PEOPLE_SEED integer)")
    }

    private fun createTeamTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_TEAM " +
                "($TEAM_NAME text primary key " +
                ", $TEAM_A text " +
                ", $TEAM_B text " +
                ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            onCreate(db)
        }
    }

    fun putPeople(people: People) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PEOPLE_NAME, people.name)
        values.put(PEOPLE_SEED, people.seed)
        Log.d("ADD_PARADISE", "putPeople p is $people" )
        db.insert(TABLE_PEOPLE, null, values)
    }


    fun putTeam(team: Team) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TEAM_NAME, team.name)
        values.put(TEAM_A, team.personA)
        values.put(TEAM_B, team.personB)
        db.insert(TABLE_TEAM, null, values)
    }

    fun getPeople(): List<People> {
        val res = mutableListOf<People>()
        val db = this.readableDatabase
        val cur = db.query(
                TABLE_PEOPLE,
                arrayOf(PEOPLE_NAME, PEOPLE_SEED),
                null, null, null, null, null)
        cur.moveToFirst()
        Log.d("ADD_PARADISE", "getPeople cur count is ${cur.count}" )

        while (!cur.isAfterLast) {
            val p = People(
                    cur.getString(0),
                    cur.getInt(1)
            )
            Log.d("ADD_PARADISE", "getPeople while : p is $p" )

            res.add(p)
            cur.moveToNext()
        }

        cur.close()
        return res
    }

    fun getTeam(): List<Team> {
        val res = mutableListOf<Team>()
        val db = this.readableDatabase
        val cur = db.query(
                TABLE_TEAM,
                arrayOf(TEAM_NAME, TEAM_A, TEAM_B),
                null, null, null, null, null)
        cur.moveToFirst()
        while (!cur.isAfterLast) {
            res.add(Team(
                    cur.getString(0),
                    cur.getString(1),
                    cur.getString(2)
            ))
            cur.moveToNext()
        }

        cur.close()
        return res
    }

    fun removePeople(people: People) {
        val db = this.writableDatabase
        db.delete(TABLE_PEOPLE, "$PEOPLE_NAME='${people.name}'", null)
    }

    fun clearPeople() {
        val db = this.writableDatabase
        db.execSQL("delete from $TABLE_PEOPLE")
    }

    fun dropTables() {
        dropTable(TABLE_TEAM)
        dropTable(TABLE_PEOPLE)
    }

    private fun dropTable(string: String) {
        this.writableDatabase.execSQL(
                "DROP TABLE IF EXISTS $string"
        )
    }

}