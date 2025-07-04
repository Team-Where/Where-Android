package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.datastore.AppManageDataStore
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.TokenStatus
import com.sooum.domain.usecase.LoadDataWhenLoginUseCase
import com.sooum.domain.usecase.auth.VersionCheckUseCase
import com.sooum.domain.usecase.user.CheckUserTokenExpiredUseCase
import com.sooum.domain.util.AppVersionProvider
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appManageDataStore: AppManageDataStore,
    private val versionCheckUseCase: VersionCheckUseCase,
    private val appVersionProvider: AppVersionProvider,
    private val checkUserTokenExpiredUseCase: CheckUserTokenExpiredUseCase,
    private val loadDataWhenLoginUserCase: LoadDataWhenLoginUseCase,
) : ViewModel() {

    fun checkSplash(
        needUpdate: () -> Unit,
        complete: (dest: ScreenRoute) -> Unit,
        onVersionCheckFailed: () -> Unit
    ) {
        viewModelScope.launch {
            val timeJob = async {
                //3초대기
                delay(3000L)
            }
            val checkLogin = async {
                checkUserTokenExpiredUseCase()
            }
            val checkAppUpdate = async {
                val version = appVersionProvider.getVersionName()

                when (val versionResult = versionCheckUseCase(version).firstOrNull()) {
                    is ApiResult.Success -> versionResult.data
                    else -> null
                }
            }
            val isFirstLaunch = async {
                //최초 첫 실행 인지 확인
                appManageDataStore.getIsFirstLaunch().first()
            }
            joinAll(timeJob, checkLogin, checkAppUpdate, isFirstLaunch)
            val isNewVersion = checkAppUpdate.await()
            if (isNewVersion == null) {
                onVersionCheckFailed()
                return@launch
            }
            if (!isNewVersion) {
                needUpdate()
            } else {
                val result = checkLogin.await()
                val isFirst = isFirstLaunch.await()
                val dest = if (result == TokenStatus.NOT_EXPIRED) {
                    //유저정보가 있는 경우 데이터를 미리 로드해준다.
                    appManageDataStore.getUserId().firstOrNull()
                        ?.let {
                            loadDataWhenLoginUserCase(it)
                        }
                    //이미 로그인 되어있다면 Main으로 바로 가기
                    ScreenRoute.HomeRoute
                } else {
                    //로그인 되어있지 않다면
                    if (isFirst) {
                        //첫 실행인 경우 온보딩
                        ScreenRoute.OnBoarding
                    } else {
                        //로그인 화면으로...
                        ScreenRoute.AuthRoute
                    }
                }
                complete(dest)
            }
        }
    }
}