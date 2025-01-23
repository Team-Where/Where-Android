package com.sooum.where_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page1); // login_page1.xml과 연결

        // ImageButton 초기화
        ImageButton btnBack = findViewById(R.id.btnBack);

        // 클릭 이벤트 추가
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainLoginActivity로 이동
                Intent intent = new Intent(LoginPage1Activity.this, MainLoginActivity.class);
                startActivity(intent);

                // 현재 Activity 종료 (선택 사항)
                finish();
            }
        });
    }
}
