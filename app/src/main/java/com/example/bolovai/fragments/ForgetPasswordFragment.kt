package com.example.bolovai.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bolovai.R
import com.example.bolovai.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password){
    private lateinit var binding: FragmentForgetPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentForgetPasswordBinding.bind(view)
        val forPas=binding.forPas.text.toString()
        val ttb= AnimationUtils.loadAnimation(activity,R.anim.ttb)
        val stb= AnimationUtils.loadAnimation(activity,R.anim.stb)

        binding.blueCat.startAnimation(stb)
        binding.linearLayout.startAnimation(ttb)


        binding.ResetButton.setOnClickListener() {

            if (binding.forPas.text.isEmpty()) {
                Toast.makeText(activity, "enter you email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(forPas).addOnCompleteListener { reset ->
                if(reset.isSuccessful){
                    Toast.makeText(activity, "check your email", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(activity, "email doesn't exist", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }
}