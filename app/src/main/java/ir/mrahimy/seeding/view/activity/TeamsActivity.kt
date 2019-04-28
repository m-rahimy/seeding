package ir.mrahimy.seeding.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import ir.mrahimy.seeding.R
import ir.mrahimy.seeding.db.DbManager
import ir.mrahimy.seeding.entity.Team
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        title = getString(R.string.teams)
        val db = DbManager(this)

        val people = db.getPeople()

        val seed1 = people.filter { it.seed == 1 }.shuffled().shuffled()
        val seed2 = people.filter { it.seed == 2 }.shuffled().shuffled()

        val teams = seed1 zip seed2

        teams.forEach {
            val team = Team(
                    "${it.first.name}_${it.second.name}",
                    it.first.name,
                    it.second.name
            )
            db.putTeam(team)

            val view = LayoutInflater.from(this).inflate(R.layout.team_card, null)
            val name = view.findViewById<TextView>(R.id.nameTxt)
            name.text = getString(R.string.team_name, team.personA, team.personB)
            teamsLayout?.addView(view)
        }
    }
}
