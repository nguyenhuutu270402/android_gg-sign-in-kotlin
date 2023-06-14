package com.example.ggsigninkotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ggsigninkotlin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private  lateinit var  auth: FirebaseAuth
    private  lateinit var  googleSignInClient: GoogleSignInClient


    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv0.text = "Hãy kiểm tra những điểm trên và đảm bảo rằng bạn đã đáp ứng đầy đủ các yêu cầu và xử lý lỗi một cách chính xác để có thể hiển thị mã QR thành công. Nếu vấn đề vẫn tiếp tục, hãy cung cấp thêm thông tin cụ thể về mã lỗi hoặc thông báo lỗi mà bạn nhận được để chúng tôi có thể cung cấp hỗ trợ chi tiết hơn."

        //
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener {
            signInGoogle()
        }

        binding.btnLogout.setOnClickListener {
            signOutGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                val gson = Gson()
                val json = gson.toJson(account)
                binding.tv0.text = json
                doAfterLoginSocialSuccess(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOutGoogle() {
        googleSignInClient.signOut().addOnCompleteListener(this) {
            // Đăng xuất thành công, thực hiện các tác vụ sau đăng xuất
            // Ví dụ: Cập nhật UI hoặc chuyển hướng đến màn hình đăng nhập
            binding.tv0.text = "Đã đăng xuất"
        }
    }

    private fun doAfterLoginSocialSuccess(account: GoogleSignInAccount) {
        val  credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent: Intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email", account.email);
                intent.putExtra("displayName", account.displayName);
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}