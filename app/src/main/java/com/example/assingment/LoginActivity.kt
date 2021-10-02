package com.example.assingment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerbutton = findViewById<Button>(R.id.registerhere)
        registerbutton.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        val login = findViewById<Button>(R.id.login)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        login.setOnClickListener {
            when{
                TextUtils.isEmpty(email.text.toString().trim{it<=' '}) ->{
                    Toast.makeText(this@LoginActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(password.text.toString().trim{it<=' '}) ->{
                    Toast.makeText(this@LoginActivity,
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
                                    this@LoginActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
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


