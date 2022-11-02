# Pixabay

### Description
Android app that fetches images from the pixabay API based on one or more search terms.

### Technologies & Libraries
- Kotlin, kotlin flows and coroutines
- UI with Jetpack Compose and Material Design 3
- Image loading with Coil
- Caching with Room Database
- Network calls with Retrofit and JSON management with Gson

## Architecture & Patterns
- Clean architecture by feature with SOLID principles
- MVVM mixed with MVI for UI state management

## Notes
Jetpack Compose has been used for the UI part. This means, no XML files and no fragments are needed and no data bindig/view binding is needed, just composable functions. Due to this, Coil has been used for the image loading, as right know is the only library with compatibility. Moreover, RxJava has been replaced for kotlin coroutines, flows and suspend functions, which are all kotlin native features.
