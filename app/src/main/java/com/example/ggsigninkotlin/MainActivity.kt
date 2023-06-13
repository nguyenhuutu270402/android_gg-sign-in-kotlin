package com.example.ggsigninkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ggsigninkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv0.text = "Hãy kiểm tra những điểm trên và đảm bảo rằng bạn đã đáp ứng đầy đủ các yêu cầu và xử lý lỗi một cách chính xác để có thể hiển thị mã QR thành công. Nếu vấn đề vẫn tiếp tục, hãy cung cấp thêm thông tin cụ thể về mã lỗi hoặc thông báo lỗi mà bạn nhận được để chúng tôi có thể cung cấp hỗ trợ chi tiết hơn."

    }
}