# HouseGOT
An app that shows all the houses of GOT (from an API) in a List and shows the details of each house in a detail view.


## Screenshots

<img src="https://github.com/Czeach/HouseGOT/blob/master/screenshots/photo_2023-03-13%2011.07.36.png" width="250" height="500" /> <img src="https://github.com/Czeach/HouseGOT/blob/master/screenshots/photo_2023-03-13%2011.07.40.png" width="250" height="500" />

## Tech stack and Libraries used
* Minimum SDK level: 23
* Kotlin, 100% Jetpack Compose, [Coroutines](https://developer.android.com/kotlin/coroutines) and Flow for asynchronous programming
* Jetpack libraries
    * [Jetpack Compose](https://developer.android.com/jetpack/compose): Android’s modern toolkit for building native UI
    * [Navigation Compose](https://developer.android.com/jetpack/compose/navigation): Navigate between composables while leveraging of the Navigation component’s infrastructure and features
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Encapsulates related business logic and manage UI data in a lifecycle-aware fashion
    * [Room](https://developer.android.com/training/data-storage/room): Persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite
    * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): Library helps load and display pages of data from a larger dataset from local storage or over network.
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Standard way to incorporate Dagger dependency injection into an Android application that reduces boilerplate code
    * [Retrofit2](https://github.com/square/retrofit): Type-safe REST client for Android and Java
    * [Gson](https://github.com/google/gson): Java library that can be used to convert Java Objects into their JSON representation and vice versa
* Architecture
    * MVVM Architecture
    * Repository pattern
* JUnit and Mockito for testing


## Architecture
This app follows [Google's official architecture guidance](https://developer.android.com/topic/architecture). It is based on the MVVM architecture and the Repository pattern.

Article: https://towardsdev.com/pagination-in-android-using-paging-3-and-room-database-for-caching-e226ff8db337

## LICENSE
```
Apache License

Copyright 2023 Ezichi Amarachi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
