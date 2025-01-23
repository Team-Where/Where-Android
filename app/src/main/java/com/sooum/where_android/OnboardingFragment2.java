package com.sooum.where_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnboardingFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onbording_page2, container, false);

        // 건너뛰기 클릭 이벤트
        TextView tvSkip = view.findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(v -> {
            if (getActivity() instanceof OnboardingActivity) {
                ((OnboardingActivity) getActivity()).moveToPage(2); // 페이지 3으로 이동 (인덱스: 2)
            }
        });

        return view;
    }
}
