package com.everton.messengerdev

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.act_reg.*

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_reg)

        btn_reg.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val email = edit_cadEmail.text.toString()
        val password = editCadTextPassword.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this, "email e senhas devem ser informados",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("teste", "UserID eh ${it.result?.user?.uid}")
                }
            }.addOnFailureListener {
                Log.e("teste", it.message, it)


            }
    }
}