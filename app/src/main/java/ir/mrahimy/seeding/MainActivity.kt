package ir.mrahimy.seeding

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = DbManager(this)

        addButton?.setOnClickListener {
            if (nameEdt?.text.toString().isNotEmpty()) {
                val p = People(nameEdt?.text.toString(), 1)
                Log.d("ADD_PARADISE", "addButton?.setOnClickListener adding $p to db")
                db.putPeople(p)
                Log.d("ADD_PARADISE", "addButton?.setOnClickListener put $p to db")
                clearFields(nameEdt)
                Log.d("ADD_PARADISE", "addButton?.setOnClickListener cleared fields")
                getPeopleFromDatabase(db)
            }
        }

        getPeopleFromDatabase(db)

        makeTeamsButton?.setOnClickListener {
            startActivity(Intent(this, TeamsActivity::class.java))
        }

        clearButton?.setOnClickListener {
            db.clearPeople()
            removeChildren(seed1Layout)
            removeChildren(seed2Layout)
        }

        sampleButton?.setOnClickListener {
            init(db)
        }
    }

    private fun init(db: DbManager) {
        val list = listOf<People>(
                People("Reza", 2),
                People("Sadeq", 1),
                People("Mojtaba", 1),
                People("Asghar", 1),
                People("Elham", 2),
                People("Elahe", 2)
        )

        list.forEach {
            db.putPeople(it)
        }

        getPeopleFromDatabase(db)
    }

    private fun getPeopleFromDatabase(db: DbManager) {
        removeChildren(seed1Layout)
        removeChildren(seed2Layout)
        val people = db.getPeople()
        Log.d("ADD_PARADISE", "getPeopleFromDatabase people are $people")
        people.forEach {
            Log.d("ADD_PARADISE", "people.forEach p is  $it")
            if (it.seed == 1) {
                putOnLeftSide(db, it)
            } else {
                putOnRightSide(db, it)
            }
        }
    }

    private fun removeChildren(layout: LinearLayout?) {
        /*val last = layout?.childCount ?: 0
        (0 until last).forEach {
            layout?.removeViewAt(last)
        }*/
        layout?.removeAllViews()
    }

    private fun putOnRightSide(db: DbManager, people: People) {
        Log.d("ADD_PARADISE", "putOnRightSide adding $people to RightSide")
        putOnLayout(db, people, seed2Layout, 1)
    }

    private fun putOnLeftSide(db: DbManager, people: People) {
        Log.d("ADD_PARADISE", "putOnLeftSide adding $people to LeftSide")
        putOnLayout(db, people, seed1Layout, 2)
    }

    private fun putOnLayout(db: DbManager, people: People, seedLayout: LinearLayout?, otherSeed: Int) {
        val view = LayoutInflater.from(this).inflate(R.layout.people_card, null)
        view.findViewById<TextView>(R.id.nameTxt).text = people.name
        val moveButton = view.findViewById<Button>(R.id.moveButton)
        val moveText = if (otherSeed > people.seed!!) {
            ">"
        } else {
            "<"
        }
        moveButton.text = moveText

        moveButton.setOnClickListener {
            val p2 = People(
                    people.name,
                    otherSeed

            )
            db.removePeople(people)
            db.putPeople(p2)
            getPeopleFromDatabase(db)
        }

        view.findViewById<Button>(R.id.removeButton).setOnClickListener {
            db.removePeople(people)
            getPeopleFromDatabase(db)
        }

        seedLayout?.addView(view)

    }

    private fun clearFields(edt: EditText?) {
        edt?.text?.clear()
        edt?.setText("")
    }
}
