package com.everton.messengerdev

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.item_user.view.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var mAdapter: GroupAdapter<ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        mAdapter = GroupAdapter()
        list_contact.adapter =mAdapter

        fetchUsers()
    }

    private inner class UserItem (internal val mUser:User):Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.txt_username.text = mUser.name
            Picasso.get()
                    .load(mUser.url)
                    .into(viewHolder.itemView.img_photo)
        }

        override fun getLayout(): Int {
           return R.layout.item_user
        }

    }

    private fun fetchUsers() {
        FirebaseFirestore.getInstance().collection("/users/")
                .addSnapshotListener { snapshot, exception ->
                    exception?.let {
                        Log.d("Teste", it.message, it)
                        return@addSnapshotListener
                    }

                    snapshot ?.let {
                        for (doc in snapshot) {
                            val user = doc.toObject(User :: class .java)
                            Log.i("Teste", "user ${user.uid}, ${user.name}")
                        }
                    }
                }
    }
}