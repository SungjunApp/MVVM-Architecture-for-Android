# Android MVVM Architecture with coroutines


Android MVVM Architecture with coroutines is a project to showcase different architectural approaches to developing Android apps. In its different branches you'll find the same app (a TODO app) implemented with small differences.

In this branch you'll find:
*   Kotlin **[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)** for background operations.
*   A single-activity architecture.
*   A presentation layer that contains a fragment (View) and a **ViewModel** per screen (or feature).
*   Reactive UIs using **LiveData** observables.
*   A **data source layer** with a repository and.
*   Two **product flavors**, `dev`, `mock` and `prod`, [to ease development and testing](https://android-developers.googleblog.com/2015/12/leveraging-product-flavors-in-android.html) (except in the Dagger branch).
*   A collection of unit, integration and e2e **tests**, including "shared" tests that can be run on emulator/device or Robolectric.
