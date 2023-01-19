package com.example.bolovai.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.bolovai.Data
import com.example.bolovai.R
import com.example.bolovai.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment(R.layout.fragment_sign_up){
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var database: DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentSignUpBinding.bind(view)




        val ttb= AnimationUtils.loadAnimation(activity,R.anim.ttb)
        binding.linearLayout.startAnimation(ttb)
        binding.SignUp.setOnClickListener(){
            val email=binding.SignInEmail.text.toString()
            val pas=binding.SignInPassword.text.toString()
            val username=binding.SignUpUserName.text.toString()
            val firstname=binding.FirstName.text.toString()
            val lastname=binding.LastName.text.toString()


            if (binding.FirstName.text.toString().isEmpty()||binding.LastName.text.toString().isEmpty()
                ||binding.SignInEmail.text.toString().isEmpty()||binding.SignInPassword.text.toString().isEmpty()
                ||binding.SignInPassword.text.toString().isEmpty()||binding.RepeatPassword.text.toString().isEmpty()){
                Toast.makeText(activity, "Please,Complete all required fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (!binding.check.isChecked){
                Toast.makeText(activity, "Accept the terms and conditions\n if you want to proceed", Toast.LENGTH_LONG).show()
            } else if (binding.SignInPassword.text.toString() != binding.RepeatPassword.text.toString()) {
                Toast.makeText(activity, "password is not correct", Toast.LENGTH_SHORT).show()
            } else {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pas).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        val useruid = FirebaseAuth.getInstance().currentUser?.uid
                        database=FirebaseDatabase.getInstance().getReference("Users")
                        val user=Data(useruid,firstname, lastname, username, email)

                        database.child("${useruid}").setValue(user).addOnSuccessListener {
                            binding.SignInEmail.text.clear()
                            binding.SignInPassword.text.clear()
                            binding.RepeatPassword.text.clear()
                            binding.SignUpUserName.text.clear()
                            binding.FirstName.text.clear()
                            binding.LastName.text.clear()
                            !binding.check.isChecked
                            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener {


                                    Toast.makeText(activity, "please verify", Toast.LENGTH_LONG)
                                        .show()
                                }
                                ?.addOnFailureListener {
                                    Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                }

                        }


                    } else {
                        Toast.makeText(activity, "nope", Toast.LENGTH_SHORT).show()
                    }


                }


            }
        }

    }
}