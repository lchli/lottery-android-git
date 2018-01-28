package com.lch.lottery.user.presenter;


import com.blankj.utilcode.util.LogUtils;
import com.lch.lottery.ApiUrl;
import com.lch.lottery.user.data.UserRepo;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.NetKit;
import com.lch.netkit.common.tool.JsonHelper;
import com.lch.netkit.string.Callback;
import com.lch.netkit.string.Parser;
import com.lch.netkit.string.StringRequestParams;

public interface RegisterContract {


    interface View {

        void onSuccess(UserResponse.User data);

        void onFail(String msg);

    }

    interface Presenter {

        void registerView(View view);

        void unregisterView();

        void register(String userName, String pwd, String repwd);
    }

    class PresenterImpl implements Presenter {

        private View view;

        @Override
        public void registerView(View view) {
            this.view = view;
        }

        @Override
        public void unregisterView() {
            this.view = null;
        }

        @Override
        public void register(String userName, String pwd, String repwd) {

            StringRequestParams params = new StringRequestParams()
                    .setUrl(ApiUrl.REGISTER)
                    .addParam("userName", userName)
                    .addParam("userPwd", pwd);

            NetKit.stringRequest().post(params, new Parser<UserResponse.User>() {
                @Override
                public UserResponse.User parse(String s) {
                    LogUtils.e(s);

                    UserResponse response = JsonHelper.parseObject(s, UserResponse.class);
                    if (response == null) {
                        throw new IllegalStateException("服务器数据解析失败");

                    }
                    if (response.status != ApiUrl.API_CODE_SUCCESS || response.data == null) {
                        throw new IllegalStateException(response.message);
                    }
                    UserRepo.saveUser(response.data);

                    return response.data;

                }
            }, new Callback<UserResponse.User>() {
                @Override
                public void onSuccess(UserResponse.User user) {

                    if (view != null) {
                        view.onSuccess(user);
                    }
                }

                @Override
                public void onFail(String s) {
                    if (view != null) {
                        view.onFail(s);
                    }
                }
            });


        }
    }

}
