package com.example.pazig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewAppointment : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var id: TextView
    private lateinit var date: TextView
    private lateinit var description: TextView
    private lateinit var humor: TextView
    private lateinit var medications: TextView

    private lateinit var apid:String
    private lateinit var apdate: String
    private lateinit var apdescription: String
    private lateinit var aphumor: String
    private lateinit var apmedications: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_appointment)

        val i1 = intent
        val mailid = i1.getStringExtra("atwo")
        val appId = mailid?.subSequence(0, 5).toString()

        database = Firebase.database.reference
        fun writeNewApp(appId: String, appointmentID: String, appointmentdate: String, appointmentdescription: String, humor: String, medications: String ) {
            val appointment = Appointment(appointmentID, appointmentdate, appointmentdescription, humor, medications)
            database.child("appointments").child(appId).child(apid).setValue(appointment)
        }


        val saveData: Button =findViewById(R.id.saveappNewApp)
        saveData.setOnClickListener{
            id=findViewById(R.id.app_idNewApp)
            date=findViewById(R.id.appdateNewApp)
            description=findViewById(R.id.app_descriptionNewApp)
            humor=findViewById(R.id.app_humorNewApp)
            medications=findViewById(R.id.app_medicationsNewApp)

            apid=id.text.toString().trim()
            apdate=date.text.toString().trim()
            apdescription=description.text.toString().trim()
            aphumor=humor.text.toString().trim()
            apmedications=medications.text.toString().trim()

            writeNewApp(appId, apid, apdate, apdescription, aphumor, apmedications)
            //go to app calendar
            val gotocalendar= Intent(applicationContext, Calendar::class.java)
            gotocalendar.putExtra("id", appId)
            startActivity(gotocalendar)
        }
        //goback
        val back = findViewById<Button>(R.id.backNewApp)
        back.setOnClickListener {
            val goback = Intent(applicationContext, Appointments::class.java)
            startActivity(goback)
        }
    }
}