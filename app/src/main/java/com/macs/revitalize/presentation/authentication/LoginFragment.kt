package com.macs.revitalize.presentation.authentication

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.presentation.BaseFragment
import com.macs.revitalize.LoginActivity
import com.macs.revitalize.R
import com.macs.revitalize.data.repository.UsersRepositoryImpl
import com.macs.revitalize.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProgressBar(binding.progressBar)

        // Buttons
        with (binding) {
            emailSignInButton.setOnClickListener {
                val email = binding.fieldEmail.text.toString()
                val password = binding.fieldPassword.text.toString()
                signIn(email, password)
            }
            emailCreateAccountButton.setOnClickListener {
                val email = binding.fieldEmail.text.toString()
                val password = binding.fieldPassword.text.toString()
                createAccount(email, password)
            }
            signOutButton.setOnClickListener { signOut() }
//            verifyEmailButton.setOnClickListener { sendEmailVerification() }
            reloadButton.setOnClickListener { reload() }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!

        binding.googleSignInButton.setOnClickListener { signInGoogle() }


        // Initialize Firebase Auth
        auth = Firebase.auth

        // Uncomment this if you want to test authentication
        if (auth.currentUser != null){
            signOut()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(context, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInGoogle(){
        Log.i("AUTH", "In here")
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and navigate to calendar fragment
        val currentUser = auth.currentUser
        if(currentUser != null){
//            reload();
            Log.i("Auth", "User already logged in")
//            this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCalendarFragment())
            val parentActivity = requireActivity() as LoginActivity
            parentActivity.goToMainActivity(currentUser.email!!)
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showProgressBar()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
//                    updateUI(user)
                    if (user != null) {
                        val userAddStatus = user.email?.let { UsersRepositoryImpl().addAuthUser(it) }
                        if (userAddStatus == "failure") {
                            Toast.makeText(
                                context,
                                "Failed to create database user",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        else {
                            val parentActivity = requireActivity() as LoginActivity
                            parentActivity.goToMainActivity(user.email!!)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                hideProgressBar()
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        showProgressBar()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val parentActivity = requireActivity() as LoginActivity
                    if (user != null) {
                        parentActivity.goToMainActivity(user.email!!)
                    } else {
                        Toast.makeText(context, "Something went wrong",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    binding.status.setText(R.string.auth_failed)
                }
                hideProgressBar()
            }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "reload", task.exception)
                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.fieldEmail.error = "Required."
            valid = false
        } else {
            binding.fieldEmail.error = null
        }

        val password = binding.fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.fieldPassword.error = "Required."
            valid = false
        } else {
            binding.fieldPassword.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressBar()
        if (user != null) {
            binding.status.text = getString(R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified)
            binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)

            binding.emailPasswordButtons.visibility = View.GONE
            binding.emailPasswordFields.visibility = View.GONE
            binding.signedInButtons.visibility = View.VISIBLE

            if (user.isEmailVerified) {
                binding.verifyEmailButton.visibility = View.GONE
            } else {
                binding.verifyEmailButton.visibility = View.VISIBLE
            }
        } else {
            binding.status.setText(R.string.signed_out)
            binding.detail.text = null

            binding.emailPasswordButtons.visibility = View.VISIBLE
            binding.emailPasswordFields.visibility = View.VISIBLE
            binding.signedInButtons.visibility = View.GONE
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val parentActivity = requireActivity() as LoginActivity
                parentActivity.goToMainActivity(account.email!!)
            }else{
                Toast.makeText(context, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}