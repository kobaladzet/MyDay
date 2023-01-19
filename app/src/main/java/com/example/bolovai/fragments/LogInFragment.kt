package com.example.bolovai.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bolovai.MainActivity2
import com.example.bolovai.R
import com.example.bolovai.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment(R.layout.fragment_log_in){
    private lateinit var binding: FragmentLogInBinding
    private lateinit var auth: FirebaseAuth



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        binding= FragmentLogInBinding.bind(view)
        binding.buttonLogIn.setOnClickListener {
            val vemail=binding.email.text.toString()
            val vpas=binding.password.text.toString()
            if(vemail.isEmpty()||vpas.isEmpty()){
                Toast.makeText(activity, "nonono", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(vemail,vpas)
                .addOnCompleteListener { login->
                    if (login.isSuccessful){
                        val verify= FirebaseAuth.getInstance().currentUser?.isEmailVerified
                        if (verify==true){

                            editor?.apply{ putBoolean("RememberMe", binding.remember.isChecked) }?.apply()

                            val user= FirebaseAuth.getInstance().currentUser
                            val action=LogInFragmentDirections.actionLogInFragmentToMainActivity2()
                            findNavController().navigate(action)
                            activity?.finish()




                        }else if(verify==false){
                            Toast.makeText(activity, "please verify", Toast.LENGTH_SHORT).show()
                        } else if (vemail.isEmpty()) {
                            Toast.makeText(activity, "Enter your email!", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(activity, "Enter your password!", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(activity, "Account doesn't exist", Toast.LENGTH_SHORT).show()
                    }
                }




        }
        binding.tvSignUp.setOnClickListener {
            val action=LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        binding.forgotPassword.setOnClickListener {
            val action=LogInFragmentDirections.actionLogInFragmentToForgetPasswordFragment()
            findNavController().navigate(action)
        }



    }
    override fun onStart() {
        super.onStart()
        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val rememberMeStatus = sharedPreferences!!.getBoolean("RememberMe", false)
        if (rememberMeStatus) {
            val currentUser = auth.currentUser
            if(currentUser != null){
                val intent = Intent(activity, MainActivity2::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }




}