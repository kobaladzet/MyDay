package com.example.bolovai.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bolovai.DiaryNote
import com.example.bolovai.R
import com.example.bolovai.adapters.RecyclerViewAdapter
import com.example.bolovai.databinding.FragmentHomeBinding
import com.example.bolovai.databinding.NoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var alldayList: ArrayList<DiaryNote>
    private lateinit var diaryRecyclerviewAdapter: RecyclerViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

//        var sharedPrefs = context?.getSharedPreferences("diarydate", Context.MODE_PRIVATE)
//        var editor = sharedPrefs?.edit()

        val timeglobal = Calendar.getInstance().time
        var formatterDate = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = formatterDate.format(timeglobal)


        val useruid = FirebaseAuth.getInstance().currentUser?.uid

        val recyclerview = binding.recyclerview
        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerview.layoutManager = linearLayoutManager

        alldayList = ArrayList()

        diaryRecyclerviewAdapter = RecyclerViewAdapter(alldayList)
        recyclerview.adapter = diaryRecyclerviewAdapter

        var edittext = binding.editText

        FirebaseDatabase.getInstance().getReference().child("allday")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    alldayList.clear()
                    for (postsnapshot in snapshot.children) {
                        var current = postsnapshot.getValue(DiaryNote::class.java)
                        alldayList.add(current!!)

                    }

                    diaryRecyclerviewAdapter.notifyDataSetChanged()
                    recyclerview.scrollToPosition(alldayList.size - 1)



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        class NoteDialogFragment : DialogFragment() {
            private lateinit var binding: NoteBinding

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                binding = NoteBinding.inflate(inflater, container, false)

                return binding.root
            }


            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)



                binding.saveTV.setOnClickListener {

                        val timeglobal = Calendar.getInstance().time
                        val formatter = SimpleDateFormat("HH:mm")
                        var formatterDate = SimpleDateFormat("yyyy-MM-dd")

                        val time = formatter.format(timeglobal)
                        val date = formatterDate.format(timeglobal)

                        var textnote = binding.editTextnote.text.toString()
                        var mood = ""

                        if (binding.checkBox.isChecked) {

                            mood = binding.checkBox.text.toString()

                        }else if (binding.checkBox2.isChecked) {

                        mood = binding.checkBox2.text.toString()
                        }else if (binding.checkBox4.isChecked) {

                            mood = binding.checkBox4.text.toString()
                        }else if (binding.checkBox5.isChecked) {

                            mood = binding.checkBox5.text.toString()
                        }else if (binding.checkBox6.isChecked) {

                            mood = binding.checkBox6.text.toString()
                        }
                        var diaryObject = DiaryNote(textnote, mood, time, date)
                        FirebaseDatabase.getInstance().getReference()
                            .child("allday")
                            .push()
                            .setValue(diaryObject)

                        FirebaseDatabase.getInstance().getReference()
                            .child("diarynotes")
                            .child(useruid.toString())
                            .push()
                            .setValue(diaryObject)


//                        editor?.putString("date", date)
//                        editor?.apply()

                        edittext.clearFocus()
                        dismiss()


                }


                binding.exitIV.setOnClickListener {
                    edittext.clearFocus()
                    dismiss()
                }
            }
        }

        edittext.setOnFocusChangeListener { view, b ->
            if (b) {
                showDialog(NoteDialogFragment())
                binding.editText.clearFocus()
            }
        }


    }

    fun showDialog(dialogFragment: DialogFragment) {
        val fragmentManager = parentFragmentManager
        val newFragment = dialogFragment

        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

    }
}
