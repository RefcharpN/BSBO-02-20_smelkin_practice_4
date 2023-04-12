package ru.mirea.smelkin.looper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;


public class MyLooper extends Thread{
    public Handler mHandler;
    private Handler mainHandler;
    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {

                String prof = msg.getData().getString("prof");
                Integer exp = Integer.parseInt(msg.getData().getString("exp"));



                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", String.format("ваша проффесия: %s; ваш стаж: %s", prof, exp));
                message.setData(bundle);

                try {
                    TimeUnit.SECONDS.sleep(exp);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
