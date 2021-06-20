package com.everton.messengerdev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.act_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)

        btn_enter.setOnClickListener {
            signIn()
        }

      txt_account.setOnClickListener{
           val intent = Intent(this,RegActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(){
        val email = edit_email.text.toString()
        val password =editTextTextPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "email e senha devem ser informados", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("teste", "login feito ${it.result?.user?.uid}")
                }
            }.addOnFailureListener {
                Log.e("teste", it.message, it)

            }
    }
}