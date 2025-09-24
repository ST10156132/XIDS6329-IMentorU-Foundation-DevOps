package com.example.wilmentorship.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Composable
fun MfaEnrollScreen(
    auth: FirebaseAuth,
    onCodeSent: (verificationId: String) -> Unit,
    onError: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Enroll Phone for MFA", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )
        Button(onClick = {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(null) // replace with current Activity reference
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(cred) { }
                    override fun onVerificationFailed(e: Exception) {
                        onError(e.message ?: "Verification failed")
                    }
                    override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                        onCodeSent(id)
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }) {
            Text("Send Code")
        }
    }
}