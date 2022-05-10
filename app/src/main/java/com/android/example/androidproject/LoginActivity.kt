package com.android.example.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.example.androidproject.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()

        with (binding) {
            loginDoneButton.setOnClickListener {
                val email = usernameLogin.text.toString()
                val password = passwordLogin.text.toString()
                signIn(email, password)
            }

            loginRedirectButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            loginFacebookButton.setOnClickListener {
                signInWithFacebook();
            }
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm(binding.usernameLogin, binding.passwordLogin)) {
            return
        }
        val progressBar = binding.loadingLogin
        progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(applicationContext, "Successfully logged in.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                if (!task.isSuccessful) {
//                    binding.status.setText(R.string.auth_failed)
                }
            }
    }

    private fun signInWithFacebook() {
        binding.loginFacebookButton.setPermissions("email", "public_profile")
        binding.loginFacebookButton.registerCallback(callbackManager, object:FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Authentication cancelled.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Authentication failed. $error",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Successfully authenticated with facebook.",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Facebook Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "AuthMessage"
    }
}