package com.android.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.example.androidproject.databinding.FragmentLoggedInBinding
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoggedInFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth: FirebaseAuth = Firebase.auth
        val binding = DataBindingUtil.inflate<FragmentLoggedInBinding>(inflater,
            R.layout.fragment_logged_in, container, false)

        binding.userEmailField.text = auth.currentUser?.email;
        binding.logoutButton.setOnClickListener { view : View ->
            auth.signOut()
            LoginManager.getInstance().logOut();
            view.findNavController().navigate(R.id.action_loggedInFragment_to_titleFragment)
            Toast.makeText(context, "Successfully logged out",
                Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}