package com.sooum.where_android;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);

        // 어댑터 연결
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);

        // 페이드 효과 추가
        viewPager.setPageTransformer((page, position) -> {
            page.setAlpha(1 - Math.abs(position));
            page.setTranslationX(page.getWidth() * -position);
        });
    }

    // 페이지 이동 메서드
    public void moveToPage(int pageIndex) {
        if (viewPager != null) {
            viewPager.setCurrentItem(pageIndex, true); // 애니메이션과 함께 이동
        }
    }
}
