package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.datatransport.BuildConfig
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.onboarding.OnBoardingActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appManageDataStore: AppManageDataStore
) : ViewModel() {

    private val _currentVersionCode = MutableStateFlow(0)
    private val currentVersionCode: StateFlow<Int> = _currentVersionCode.asStateFlow()

    private val _serverVersionCode = MutableStateFlow(0)
    private val serverVersionCode: StateFlow<Int> = _serverVersionCode.asStateFlow()

    fun setCurrentVersionCode(value: Int) {
        _currentVersionCode.value = value
    }

    fun setServerVersionCode(value: Int) {
        _serverVersionCode.value = value
    }

    fun checkSplash(
        needUpdate: () -> Unit,
        complete: (dest: Class<*>) -> Unit
    ) {
        viewModelScope.launch {
            val timeJob = async {
                //3초대기
                delay(3000L)
            }
            val checkLogin = async {
                //TODO 환님 => 로그인 되어있는지 확인(토큰 확인 등)
                true
            }
            val checkAppUpdate = async {
                val currentVersionCode = currentVersionCode.value
                val latestVersionCodeFromServer = 1
                currentVersionCode < latestVersionCodeFromServer
            }
            val isFirstLaunch = async {
                //최초 첫 실행 인지 확인
                appManageDataStore.getIsFirstLaunch().first()
            }
            joinAll(timeJob, checkLogin, checkAppUpdate, isFirstLaunch)
            val isNewVersion = checkAppUpdate.await()
            if (isNewVersion) {
                needUpdate()
            } else {
                val result = checkLogin.await()
                val isFirst = isFirstLaunch.await()
                val dest = if (result) {
                    //이미 로그인 되어있다면 Main으로 바로 가기
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
}