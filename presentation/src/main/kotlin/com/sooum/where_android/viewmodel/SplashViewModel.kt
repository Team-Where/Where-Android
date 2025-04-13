package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.onboarding.OnBoardingActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appManageDataStore: AppManageDataStore
) : ViewModel() {

    fun checkSplash(
        complete: (dest: Class<*>) -> Unit
    ) {
        viewModelScope.launch {
            val timeJob = async {
                //3초대기
                delay(3000L)
            }
            val checkLogin = async {
                //로그인 되어있는지 홗인(토큰 확인 등)
                false
            }
            val isFirstLaunch = async {
                //최초 첫 실행 인지 확인
                appManageDataStore.getIsFirstLaunch().first()
            }
            joinAll(timeJob, checkLogin, isFirstLaunch)
            val result = checkLogin.await()
            val isFirst = isFirstLaunch.await()
            val dest = if (result) {
                //이미 로그인 되어있다면 Main으로 바로 가기
                //TODO 로그인 연결 처리?
                MainActivity::class.java
            } else {
                //로그인 되어있지 않다면
                if (isFirst) {
                    //첫 실행인 경우 온보딩
                    OnBoardingActivity::class.java
                } else {
                    //로그인 화면으로...
                    AuthActivity::class.java
                }
            }
            complete(dest)
        }
    }
}