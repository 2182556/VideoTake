package com.videotake;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoTake extends Application {
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
}
