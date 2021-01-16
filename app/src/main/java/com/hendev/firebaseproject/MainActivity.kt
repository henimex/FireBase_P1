package com.hendev.firebaseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        val refKisiler = database.getReference("kisiler")

        //val kisi = Kisiler("Dilek", 35)
        //refKisiler.push().setValue(kisi)

        btnPush.setOnClickListener {
            val name = edtName.text.toString().trim()
            val age = edtAge.text.toString()

            Log.e("name",name)
            Log.e("age",age)

            val kisi = Kisiler(name, age.toInt())
            refKisiler.push().setValue(kisi)
        }

        btnDelete.setOnClickListener {
            val refid = edtReferance.text.toString()
            refKisiler.child(refid).removeValue()
        }

        btnUpdate.setOnClickListener {
            val name = edtName.text.toString().trim()
            val age = edtAge.text.toString()
            val refid = edtReferance.text.toString()

            val updateInfo = HashMap<String,Any>()

            updateInfo["kisi_ad"] = name
            updateInfo["kisi_yas"] = age.toInt()

            refKisiler.child(refid).updateChildren(updateInfo)
        }

        /*
        refKisiler.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                for (i in ds.children){
                    val kisi = i.getValue(Kisiler::class.java)
                    if (kisi != null){
                        Log.e("Ref ",i.key.toString())
                        Log.e("Ad  ",kisi.kisi_ad.toString())
                        Log.e("Ref ",kisi.kisi_yas.toString())
                        Log.e("********* ","**********")
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
*/
        val sorgu = refKisiler.orderByChild("kisi_ad").equalTo("Ferhat")

        sorgu.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                for (i in ds.children){
                    val kisi = i.getValue(Kisiler::class.java)
                    if (kisi != null){
                        Log.e("Aranan Ref ",i.key.toString())
                        Log.e("Aranan Ad  ",kisi.kisi_ad.toString())
                        Log.e("Aranan Ref ",kisi.kisi_yas.toString())
                        Log.e("********* ","**********")
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }
}