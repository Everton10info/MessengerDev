package com.everton.messengerdev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

      txt_account.setOnClickListener{
           val intent = Intent(this,RegActivity::class.java)
            startActivity(intent)
        }
    }
}