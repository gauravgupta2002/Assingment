package com.example.assingment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login = findViewById<Button>(R.id.loginhere)
        login.setOnClickListener { super.onBackPressed() }

        val register = findViewById<Button>(R.id.registerbutton)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        register.setOnClickListener {
            when{
                TextUtils.isEmpty(email.text.toString().trim{it<=' '}) ->{
                    Toast.makeText(this@RegisterActivity,
                    "Please Enter Email",
                    Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(password.text.toString().trim{it<=' '}) ->{
                    Toast.makeText(this@RegisterActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val email:String = email.text.toString().trim{it<=' '}
                    val password:String = password.text.toString().trim{it<=' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You are logged in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

    }
}