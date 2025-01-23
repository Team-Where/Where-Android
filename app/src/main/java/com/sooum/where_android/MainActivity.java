package com.sooum.where_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 온보딩 페이지로 이동
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish(); // MainActivity 종료
    }
}
