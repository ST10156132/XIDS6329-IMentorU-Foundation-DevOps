package com.example.wilmentorship.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun MfaVerifyScreen(
    verificationId: String,
    onVerified: () -> Unit,
    onError: (String) -> Unit
) {
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Enter Verification Code", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Code") }
        )
        Button(onClick = {
            try {
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                FirebaseAuth.getInstance().currentUser?.multiFactor?.enroll(credential, "Phone MFA")
                    ?.addOnSuccessListener { onVerified() }
                    ?.addOnFailureListener { e -> onError(e.message ?: "Failed") }
            } catch (e: Exception) {
                onError(e.message ?: "Invalid code")
            }
        }) {
            Text("Verify & Enroll")
        }
    }
}