package com.example.safeshare.utils.screen_state

sealed class ScreenState<T> {
    class PreLoad<T> : ScreenState<T>()
    class Loading<T> : ScreenState<T>()
    class Loaded<T>(val result: T) : ScreenState<T>()
    class Error<T>(val message: String?) : ScreenState<T>()
}