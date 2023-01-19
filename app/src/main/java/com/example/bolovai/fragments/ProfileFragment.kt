package com.example.bolovai.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bolovai.DiaryNote
import com.example.bolovai.MainActivity
import com.example.bolovai.R
import com.example.bolovai.adapters.RecyclerViewAdapter
import com.example.bolovai.databinding.ChangePasBinding
import com.example.bolovai.databinding.FragmentProfileBinding
import com.example.bolovai.databinding.NoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var selectedImage: Uri
    private lateinit var diaryNotesList : ArrayList<DiaryNote>
    private lateinit var diaryRecyclerviewAdapter: RecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding = FragmentProfileBinding.bind(view)
        database = FirebaseDatabase.getInstance().reference
        val useruid = FirebaseAuth.getInstance().currentUser?.uid

        val recyclerview = binding.profileRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(context)




        diaryNotesList = ArrayList()


        diaryRecyclerviewAdapter= RecyclerViewAdapter(diaryNotesList)
        recyclerview.adapter = diaryRecyclerviewAdapter

        database.child("diarynotes").child(useruid.toString())
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    diaryNotesList.clear()
                    for(postsnapshot in snapshot.children){
                        var current = postsnapshot.getValue(DiaryNote::class.java)
                        diaryNotesList.add(current!!)

                    }
                    diaryRecyclerviewAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        database.child("Users").child("${useruid}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val temp = snapshot
                    binding.ProfileFirstName.text = temp.child("firstname").value.toString()
                    binding.ProfileEmail.text = temp.child("email").value.toString()
                    binding.ProfileLastName.text = temp.child("lastname").value.toString()
                    binding.ProfileUserNAme.text = temp.child("username").value.toString()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        binding.defaultPfp.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }


        binding.logOut.setOnClickListener() {

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle("Log out")
                    setMessage("Are you sure you want to Log out?")
                    setPositiveButton(R.string.yes,
                        DialogInterface.OnClickListener { dialog, id ->
                            FirebaseAuth.getInstance().signOut()
                            val intent = Intent(requireContext(),MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

                        })
                    setNegativeButton(R.string.no,
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(context, "okay", Toast.LENGTH_SHORT).show()
                        })
                    setCancelable(false)
                }
                
                builder.create()
            }
            alertDialog?.show()
        }




        class ChangePassDialogFragment: DialogFragment() {
            private lateinit var binding: ChangePasBinding

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                binding = ChangePasBinding.inflate(inflater, container, false)

                return binding.root
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                binding.buttonChangePassword.setOnClickListener() {

                    if (binding.NewPasswordChange.text.toString().length <= 6) {
                        Toast.makeText(
                            activity,
                            "password should contain at least 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (binding.NewPasswordChange.text.toString() != binding.RepeatPasswordChange.text.toString()) {
                        Toast.makeText(activity, "Password is incorrect", Toast.LENGTH_SHORT).show()
                    } else if (binding.NewPasswordChange.text.toString()
                            .isEmpty() || binding.RepeatPasswordChange.text.toString().isEmpty()
                    ) {
                        return@setOnClickListener
                        Toast.makeText(activity, "Empty!", Toast.LENGTH_SHORT).show()
                    } else {
                        FirebaseAuth.getInstance().currentUser?.updatePassword(binding.NewPasswordChange.text.toString())
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(activity, "Success!!!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(activity, "Error!!1", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }

                }


            }

        }

        binding.forgotPasswordtv.setOnClickListener(){

                showDialog(ChangePassDialogFragment())

        }


    }
    fun showDialog(dialogFragment: DialogFragment) {
        val fragmentManager = parentFragmentManager
        val newFragment = dialogFragment
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImage = data.data!!
                binding.defaultPfp.setImageURI(selectedImage)

            }

        }
    }


}








