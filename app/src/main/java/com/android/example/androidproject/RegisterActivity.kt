package com.android.example.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.example.androidproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        with (binding) {
            regDoneButton.setOnClickListener {
                val email = usernameReg.text.toString()
                val password = passwordReg.text.toString()
                createAccount(email, password)
            }

            regRedirectButton.setOnClickListener {
                finish()
            }
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm(binding.usernameReg, binding.passwordReg)) {
            return
        }
        val progressBar = binding.loadingReg
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        applicationContext, "Successfully registered.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                    val user = auth.currentUser;
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        applicationContext, "Failed Registration. Invalid data.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

//    private fun validateForm(): Boolean {
//        var valid = true
//
//        val email = binding.usernameReg.text.toString()
//        if (TextUtils.isEmpty(email)) {
//            binding.usernameReg.error = "Required."
//            valid = false
//        } else {
//            binding.usernameReg.error = null
//        }
//
//        val password = binding.passwordReg.text.toString()
//        if (TextUtils.isEmpty(password)) {
//            binding.passwordReg.error = "Required."
//            valid = false
//        } else {
//            binding.passwordReg.error = null
//        }
//
//        return valid
//    }

    companion object {
        private const val TAG = "AuthMessage"
    }
}