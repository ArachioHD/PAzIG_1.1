package com.example.pazig

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Calendar : AppCompatActivity() {

    private lateinit var database: DatabaseReference


    private lateinit var apid: String
    private lateinit var apdate: String
    private lateinit var apdescription: String
    private lateinit var aphumor: String
    private lateinit var apmedications: String
    private lateinit var showapp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //show
        val show = findViewById<Button>(R.id.showinfo)
        show.setOnClickListener {

            val showid = findViewById<TextView>(R.id.app_id)
            showapp = showid.text.toString().trim()
            val ical = intent
            val mailid = ical.getStringExtra("id")

            database = FirebaseDatabase.getInstance().reference
            val postListener = object : ValueEventListener {

                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (i in dataSnapshot.children) {
                        val apid = dataSnapshot.child("appointments/$mailid/$showapp/appointmentID").value
                        val apdate = dataSnapshot.child("appointments/$mailid/$showapp/appointmentdate").value
                        val apdescription = dataSnapshot.child("appointments/$mailid/$showapp/appointmentdescription").value
                        val aphumor = dataSnapshot.child("appointments/$mailid/$showapp/humor").value
                        val apmedications = dataSnapshot.child("appointments/$mailid/$showapp/medications").value

                        val text = findViewById<TextView>(R.id.calendarshow)
                        text.text = "ID: $apid \n DATE: $apdate \n DESCRIPTION: $apdescription \n HUMOR: $aphumor \n PRESCRIBED MEDICATIONS: $apmedications \n"

                    }
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                }
            }

            database.addValueEventListener(postListener)
            database.addListenerForSingleValueEvent(postListener)

        }

        //goback
        val back = findViewById<Button>(R.id.backCal)
        back.setOnClickListener {
            val goback = Intent(applicationContext, Appointments::class.java)
            startActivity(goback)
        }
    }
}
