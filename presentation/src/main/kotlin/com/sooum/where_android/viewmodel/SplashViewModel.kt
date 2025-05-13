package com.sooum.where_android.viewmodel

import android.content.Context
import android.content.pm.PackageInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.usecase.auth.VersionCheckUseCase
import com.sooum.where_android.view.auth.AuthActivity
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.onboarding.OnBoardingActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appManageDataStore: AppManageDataStore,
    private val versionCheckUseCase: VersionCheckUseCase
) : ViewModel() {

    fun checkSplash(
        needUpdate: () -> Unit,
        complete: (dest: Class<*>) -> Unit,
        onVersionCheckFailed: () -> Unit
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
                val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                val version = info.versionName

                when (val versionResult = versionCheckUseCase(version).firstOrNull()) {
                    is ApiResult.Success -> versionResult.data
                    is ApiResult.Fail -> {
                        onVersionCheckFailed()
                        false
                    }
                    else -> false
                }
            }
            val isFirstLaunch = async {
                //최초 첫 실행 인지 확인
                appManageDataStore.getIsFirstLaunch().first()
            }
            joinAll(timeJob, checkLogin, checkAppUpdate, isFirstLaunch)
            val isNewVersion = checkAppUpdate.await()
            if (!isNewVersion) {
                needUpdate()
            } else {
                val result = checkLogin.await()
                val isFirst = isFirstLaunch.await()
                val dest = if (result) {
                    //이미 로그인 되어있다면 Main으로 바로 가기
                    AuthActivity::class.java
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