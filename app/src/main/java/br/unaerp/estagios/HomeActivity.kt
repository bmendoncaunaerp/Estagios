package br.unaerp.estagios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.unaerp.estagios.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskAdapter = TaskListAdapter()
        binding.rvTasks.adapter = taskAdapter
        binding.rvTasks.layoutManager = LinearLayoutManager(this)

        db.collection("tasks")
            .addSnapshotListener { value, error ->
                if(value != null){
                    Toast.makeText(this, "Vc tem ${value.size()} tasks", Toast.LENGTH_LONG).show()
                    val firebaseResult = value
                    val taskList: List<String> = firebaseResult.map { document ->
                        document.getString("description") ?: ""
                    }
                    taskAdapter.updateList(taskList)
                } else {
                    Toast.makeText(this, "Falha na reques", Toast.LENGTH_LONG).show()
                }
            }

        binding.btnAdd.setOnClickListener {
            db.collection("tasks").add(
                hashMapOf(
                    "description" to binding.edtNewTask.text.toString(),
                    "done" to false
                )
            ).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Falha", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}