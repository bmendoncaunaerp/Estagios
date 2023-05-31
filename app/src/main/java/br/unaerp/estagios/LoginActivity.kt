package br.unaerp.estagios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import br.unaerp.estagios.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.edtEmail.text.isEmpty()) {
                Toast.makeText(this, "Digite o email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (binding.edtPassword.text.isEmpty()) {
                Toast.makeText(this, "Digite a senha", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                auth.signInWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                ).addOnCompleteListener { result ->
                    if(result.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        //finish()
                    } else {
                        Toast.makeText(this, "Login falhou", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            // navega pra tela de cadastro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnForgot.setOnClickListener {
            auth.sendPasswordResetEmail(binding.edtEmail.text.toString()).addOnCompleteListener {
                if(it.isSuccessful) {
                    // veja seu email
                } else {
                    if(it.exception is FirebaseAuthEmailException) {
                        // email invalido
                    }
                    // ocorreu um erro
                }
            }
        }
    }
}