package com.example.bankingapp

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

public class ThreadExecutor private constructor(
    public  var diskIO: Executor,
    public  var networkIO: Executor,
    public  var mainThred: Executor
) {

    companion object{
        private  var sInstance: ThreadExecutor? = null
        private val LOCK = Any()
        fun getInstance(): ThreadExecutor? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance =
                        ThreadExecutor(
                            Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            MainThreadExecutor()
                            )
                }
            }
            return sInstance
        }

        private class MainThreadExecutor : Executor {
            private val mainThreadHandler = Handler(Looper.getMainLooper())
            override fun execute(@NonNull command: Runnable) {
                mainThreadHandler.post(command)
            }
        }
    }

}