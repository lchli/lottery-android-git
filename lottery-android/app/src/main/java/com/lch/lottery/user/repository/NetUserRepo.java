package com.lch.lottery.user.repository;

import com.apkfuns.logutils.LogUtils;
import com.lch.lottery.DI;
import com.lch.lottery.user.model.UserResponse;
import com.lch.lottery.util.ApiUrl;
import com.lch.netkit.NetKit;
import com.lch.netkit.common.tool.JsonHelper;
import com.lch.netkit.string.Parser;
import com.lch.netkit.string.ResponseValue;
import com.lch.netkit.string.StringRequestParams;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class NetUserRepo implements UserRepo {

    private UserRepo localUserRepo = DI.injectLocalUserRepo();

    @Override
    public ResponseValue<UserResponse.User> get(String username, String pwd) {
        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.LOGIN)
                .addParam("userName", username)
                .addParam("userPwd", pwd);

        return NetKit.stringRequest().getSync(params, new Parser<UserResponse.User>() {
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

                localUserRepo.add(response.data);

                return response.data;

            }
        });

    }

    @Override
    public ResponseValue<UserResponse.User> add(UserResponse.User user) {
        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.REGISTER)
                .addParam("userName", user.userName)
                .addParam("userPwd", user.userPwd);

        return NetKit.stringRequest().postSync(params, new Parser<UserResponse.User>() {

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
                localUserRepo.add(response.data);

                return response.data;

            }
        });

    }

    @Override
    public ResponseValue<UserResponse.User> update(UserResponse.User user) {
        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.USER_UPDATE)
                .addParam("userId", user.userId)
                .addParam("token", user.token)
                .addParam("userPwd", user.userPwd);

        return NetKit.stringRequest().postSync(params, new Parser<UserResponse.User>() {

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
                localUserRepo.add(response.data);

                return response.data;

            }
        });
    }

    @Override
    public ResponseValue<UserResponse.User> getCurrentUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseValue deleteCurrentUser() {
        throw new UnsupportedOperationException();
    }
}
