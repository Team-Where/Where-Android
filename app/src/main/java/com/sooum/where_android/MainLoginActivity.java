package com.sooum.where_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login); // main_login.xml 연결

        // "여기에 로그인하세요" 텍스트뷰 초기화
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

        // 클릭 이벤트 추가
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginPage1Activity로 이동
                Intent intent = new Intent(MainLoginActivity.this, LoginPage1Activity.class);
                startActivity(intent);
            }
        });
    }
}
