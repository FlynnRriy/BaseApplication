package com.baseapplication.presenter;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.baseapplication.app.MyApp;
import com.baseapplication.bean.UserBean;
import com.baseapplication.contract.MyContract;
import com.baseapplication.listener.LoginListener;
import com.baseapplication.manifest.AppManifest;
import com.baseapplication.model.LoginModelImpl;
import com.baseapplication.utils.MacUtil;
import com.baseapplication.utils.MyLog;
import com.baseapplication.utils.RsaUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;

import okhttp3.Response;


public class LoginPresenterImpl extends MyContract.LoginPresenter {
    private static final String TAG = "LoginPresenter";
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void login() {
        final MyContract.ILoginView mView = getView();
        if (mView == null) {
            MyLog.e(TAG,"请求失败 mView==null");
            return;
        }
        mView.showLoading();

//        String userName = mView.getUserName();
//        String userPassword =mView.getUserPassword();

        String mac = MacUtil.getMac(MyApp.getInstance());
        MyLog.d(TAG,"id="+mac);//d8:63:75:82:eb:6f
        String username = mac;
        String password = "wb_"+mac;
        //获取公钥
        PublicKey publicKey = RsaUtil.keyStrToPublicKey(AppManifest.PUBLIC_KEY_STR);
//        MyLog.d(TAG,"publicKey="+publicKey);
        //公钥加密结果
        String publicEncryptedResult = RsaUtil.encryptDataByPublicKey(password.getBytes(),publicKey);
//        MyLog.d(TAG,"publicEncryptedResult="+publicEncryptedResult);//737d70c99284b090
        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", publicEncryptedResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = object.toString();
//        MyLog.d(TAG,"jsonString="+jsonString);

        mIModel.requestLogin(AppManifest.host + "/user/login", jsonString, new LoginListener() {
            @Override
            public void loginSuccess( Response response) {
                String data = null;
                try {
                    data = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //{"code":200102,"message":"用户名或密码错误"}
                //{"expire_at":1544125184186,"token":"d8754db05710496a862ee1fb38476268o7Qt"}
                MyLog.d(TAG, "登录成功data="+data);

                GsonBuilder builder;
                Gson gson;
                builder = new GsonBuilder();
                gson = builder.create();

                UserBean userBean = gson.fromJson(data, UserBean.class);
                MyLog.d(TAG, "getCode="+userBean.getCode());//0
                MyLog.d(TAG, "getMessage="+userBean.getMessage());//null
                //新加的错误支线流程
                if(userBean.getCode()!=0){//说明出错了
                    MyLog.d(TAG, "登录失败 userEntity.getCode()!=0");
                    return;
                }
//                AppManifest.expire_at=userEntity.getExpire_at();
//                AppManifest.token = userEntity.getToken();

                if(mView != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.showLoginSuccess();
//                            mView.showToast("this is new mvp activity");
                        }
                    });
                }
            }
            @Override
            public void loginFailed() {
                if (mView != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.showLoginFailed();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected LoginModelImpl getModel() {
        return new LoginModelImpl();
    }

    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等
     */
    @Override
    public void afterAttach() {
    }
}