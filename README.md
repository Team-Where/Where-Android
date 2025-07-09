![7ca2150f-45b9-4361-a92b-1c701acc17e1](https://github.com/user-attachments/assets/6823c484-3501-420c-a6b5-19a60997f953)


# 어디


## Module Graph

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
    subgraph :core
        :core:notification["notification"]
        :core:alarm["alarm"]
    end
    :presentation --> :data
    :presentation --> :domain
    :presentation --> :core:notification
    :core:notification --> :core:alarm
    :data --> :domain
    :data --> :core:alarm
```

## Test

### 알림 클릭한 경우

~~~shell
adb shell am start -n com.where.android/com.sooum.where_android.view.main.MainActivity \
    -a WHERE_ALARM_1 \
    --ei meetId 14
~~~

### 앱링크 로부터 들어온경우

~~~shell
adb shell am start -a android.intent.action.VIEW \
    -d "https://audiwhere.shop/invite/49144buebo?name=임의의값" \
    com.where.android
~~~

### 스키마로부터 들어온경우

~~~shell
adb shell am start -a android.intent.action.VIEW \
    -d "audiwhere://invite/49144buebo?name=임의의값" \
    com.where.android
~~~

## 개요
모임을 만들고 초대된 사람들과 장소를 공유·의견을 나누며, 일정을 정하고 알림을 제공하는 모임 관리 앱 입니다.
+ 배포Url : 


## 기술 스택

| 분야             | 사용 기술                        |
|------------------|-----------------------------------|
| **Architecture**     | `MVVM`, `Clean Architecture`   |
| **Networking**     | `Retrofit`, `Okhttp`, `FCM`   |
| **JetPack**     | `Flow`, `coroutine`   |
| **Local Data**     | `DataStore`, `Room`   |
| **Image**     | `Glide`   |
| **Dependency Injection**     | `Hilt`   |

<table>
  <tr>
    <td align="center">
      <strong>Splash 화면</strong><br>
      <img src="https://github.com/user-attachments/assets/b6949e17-f2cd-42fb-ae9a-25ff7804d1ea" alt="Splash 화면" width="300"/>
      <img src="https://github.com/user-attachments/assets/b345a614-a964-49f6-a369-2d13262ba199" alt="Splash 화면" width="300"/>
    </td>
  </tr>

</table>
