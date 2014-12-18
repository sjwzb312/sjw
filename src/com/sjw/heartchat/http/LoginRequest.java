package com.sjw.heartchat.http;

import android.os.Handler;
import android.os.Message;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

public class LoginRequest {

    private String name;
    private String pwd;
    private int REQUEST_SUCCESS = 200;
    private LoginListener Listener;

    public LoginRequest(String name, String pwd, LoginListener listener) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.Listener = listener;
    }

    public void request() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                // EMChatManager.getInstance().createAccountOnServer(name, pwd);
                EMChatManager.getInstance().login(name, pwd, new EMCallBack() {

                    @Override
                    public void onSuccess() {

                        Message message = new Message();
                        message.what = REQUEST_SUCCESS;
                        message.obj = "登陆成功";
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onProgress(int arg0, String arg1) {

                    }

                    @Override
                    public void onError(int arg0, String arg1) {
                        Message message = new Message();
                        message.what = arg0;
                        message.obj = arg1;
                        handler.sendMessage(message);
                    }
                });

            }
        }).start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int errorCode = msg.what;
            if (errorCode == REQUEST_SUCCESS) {
                if (Listener != null) {
                    Listener.onSuccess();
                }

            } else {
                if (Listener != null) {
                    Listener.onError(msg.what, (String) (msg.obj));
                }
            }

        };
    };

    public interface LoginListener {
        void onSuccess();

        void onError(int code, String msg);

    }
}
