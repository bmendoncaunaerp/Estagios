package br.unaerp.estagios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.unaerp.estagios.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if(binding.edtEmail.text.isEmpty()) {
                Toast.makeText(this, "Digite o email", Toast.LENGTH_LONG).show()
            } else if (binding.edtPassword.text.toString() != binding.edtConfirmPassword.text.toString()) {
                Toast.makeText(this, "Confirmação de senha incorreta", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                ).addOnCompleteListener { result ->
                    if(result.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Cadastro falhou", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}


// como salvar nome do usuário
//        auth.currentUser?.updateProfile(
//            UserProfileChangeRequest.Builder()
//                .setDisplayName("Bruno")
//                .build()
//        )
