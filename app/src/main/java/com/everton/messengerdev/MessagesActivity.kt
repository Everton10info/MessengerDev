package com.everton.messengerdev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        verifyAuthentication()
    }

    private fun verifyAuthentication(){
        if(FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this@MessagesActivity, LoginActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}