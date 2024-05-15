package com.tonyxlh.documentscanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tonyxlh.documentscanner.model.User
import com.tonyxlh.documentscanner.ui.theme.lBlue

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUp()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.student),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "LECTURER SIGNUP",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))


            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "")
                }
            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            val context = LocalContext.current

            Button(onClick = {
                Register(name, email, password, context)
            },
                colors = ButtonDefaults.buttonColors(lBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                shape = RoundedCornerShape(3.dp)
            ) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.height(10.dp))


            val mContext = LocalContext.current
            Button(onClick = {
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            },
                colors = ButtonDefaults.buttonColors(lBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                shape = RoundedCornerShape(3.dp)) {
                Text(text = "SignIn")
            }
        }
    }




fun Register(name: String, email: String, password: String, context: Context) {
    val mAuth: FirebaseAuth

    mAuth = FirebaseAuth.getInstance()

    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

        var userId = mAuth.currentUser!!.uid
        var userProfile = User(name, email, password, userId)
        // Create a reference table called Users inside of the Firebase database
        var usersRef = FirebaseDatabase.getInstance().getReference()
            .child("Users/$userId")
        usersRef.setValue(userProfile).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, LoginActivity::class.java))

            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SignupPreview(){
    SignUp()
}


