package com.everton.messengerdev

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.act_reg.*
import java.util.*

class RegActivity : AppCompatActivity() {

    private var mSelectedUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reg)

        btn_reg.setOnClickListener {
            createUser()
        }

        btn_image_select.setOnClickListener{
            select_photo()
        }
    }

    private fun select_photo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,0)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==0){
            mSelectedUri = data?.data
            Log.i("Teste", mSelectedUri.toString())

            val bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver, mSelectedUri)
            img_photo.setImageBitmap(bitmap)
            btn_image_select.alpha=0f
        }
    }


    private fun createUser() {
        val email = edit_cadEmail.text.toString()
        val password = editCadTextPassword.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this, "email e senha devem ser informados",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("teste", "UserID eh ${it.result?.user?.uid}")
                    saveUserInFirebase()
                }
            }.addOnFailureListener {
                Log.e("teste", it.message, it)

            }
    }

                private fun saveUserInFirebase(){
                    val fileName = UUID.randomUUID().toString()
                    val ref = FirebaseStorage.getInstance().getReference("/images/${fileName}")

                    mSelectedUri?.let{
                        ref.putFile(it)
                            .addOnSuccessListener{
                            ref.downloadUrl.addOnSuccessListener{
                                Log.i("teste", it.toString())

                                val url = it.toString()
                                val name = edit_cadName.text.toString()
                                val uid = FirebaseAuth.getInstance().uid!!

                                var user =User (uid,name,url)

                                FirebaseFirestore.getInstance().collection("users")
                                    .add(user).addOnSuccessListener {
                                        Log.i("teste", it.id)

                                        val intent = Intent(this@RegActivity,MessagesActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener{
                                        Log.e("teste",it.message,it)
                                    }
                            }
                        }
                    }
                }
}