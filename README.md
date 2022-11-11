# Rabobank native team assignment

In this app you will find a list of users and their issue count.

# Architecture
This app uses the principles of Clean Architecture which makes it possible to create maintainable, testable and scalable applications.

The app is divided into four modules:

* `app`: Makes use of the Android framework and is used to show the UI on the screen. 
* `presenation`: Fetches data from the `domain` and emits it in the optimal UI format.
* `domain`: Contains the business logic and use cases of the app.
* `data`: Fetches data from the external API.

## Requirements
* JDK 1.8
* [Android SDK](https://developer.android.com/studio/index.html)
* Android 12 ([API 32](https://developer.android.com/preview/api-overview.html))
* Latest [Android SDK Tools](https://developer.android.com/studio) and [Android build tools](https://developer.android.com/studio/releases/build-tools)

## Libraries 
* [Kotlin](https://kotlinlang.org/)
* [Compose](https://developer.android.com/jetpack/compose)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Coil](https://coil-kt.github.io/coil/)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](http://square.github.io/okhttp/)
* [Gson](https://github.com/google/gson)
* [Mockk](https://mockk.io/)
* [Truth](https://truth.dev/)
* [JUnit](https://junit.org/junit5/docs/current/user-guide/)
