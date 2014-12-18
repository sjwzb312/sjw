package com.sjw.heartchat.http;

import android.os.Handler;
import android.os.Message;

import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

public class RegistRequest {

    private String name;
    private String pwd;
    private int REQUEST_SUCCESS = 200;
    private RegistListener registListener;

    public RegistRequest(String name, String pwd, RegistListener listener) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.registListener = listener;
    }

    public void request() {
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    EMChatManager.getInstance().createAccountOnServer(name, pwd);
                    Message message = new Message();
                    message.what = REQUEST_SUCCESS;
                    message.obj = "注册成功";
                    handler.sendMessage(message);

                } catch (EaseMobException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.obj=e;
                    handler.sendMessage(message);

                }
                
            }
        }).start();
     
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int errorCode = msg.what;
            if (errorCode == REQUEST_SUCCESS) {
                if (registListener != null) {
                    registListener.onSuccess();
                }

            } else {
                if (registListener != null) {
                    registListener.onError((EaseMobException)msg.obj);
                }
            }
  
        };
    };

    public interface RegistListener {
        void onSuccess();

        void onError(EaseMobException exception);

    }
}
