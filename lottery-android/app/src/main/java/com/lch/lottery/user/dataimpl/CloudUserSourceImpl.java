package com.lch.lottery.user.dataimpl;

import com.apkfuns.logutils.LogUtils;
import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.model.UserResponse;
import com.lch.lottery.util.ApiUrl;
import com.lch.netkit.v2.NetKit;
import com.lch.netkit.v2.apirequest.ApiRequestParams;
import com.lch.netkit.v2.common.NetworkResponse;
import com.lch.netkit.v2.parser.Parser;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.utils.tool.JsonHelper;

/**
 * Created by Administrator on 2019/1/4.
 */

public class CloudUserSourceImpl implements RemoteUserSource {
    @Override
    public ResponseValue<UserResponse.User> query(String username, String pwd) {
        ResponseValue<UserResponse.User> ret = new ResponseValue<>();

        ApiRequestParams params = new ApiRequestParams()
                .setUrl(ApiUrl.LOGIN)
                .addParam("userName", username)
                .addParam("userPwd", pwd);

        NetworkResponse<UserResponse.User> res = NetKit.apiRequest().syncGet(params, new Parser<UserResponse.User>() {
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

                return response.data;

            }
        });

        if (res.hasError()) {
            ret.setErrorMsg(res.getErrorMsg());
            ret.code = res.httpCode;
            return ret;
        }
        ret.data = res.data;

        return ret;
    }

    @Override
    public ResponseValue<UserResponse.User> add(UserResponse.User user) {
        ResponseValue<UserResponse.User> ret = new ResponseValue<>();

        ApiRequestParams params = new ApiRequestParams()
                .setUrl(ApiUrl.REGISTER)
                .addParam("userName", user.userName)
                .addParam("userPwd", user.userPwd);

        NetworkResponse<UserResponse.User> res = NetKit.apiRequest().syncPost(params, new Parser<UserResponse.User>() {

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

                return response.data;

            }
        });

        if (res.hasError()) {
            ret.setErrorMsg(res.getErrorMsg());
            ret.code = res.httpCode;
            return ret;
        }
        ret.data = res.data;

        return ret;
    }

    @Override
    public ResponseValue<UserResponse.User> update(UserResponse.User user) {
        ResponseValue<UserResponse.User> ret = new ResponseValue<>();

        ApiRequestParams params = new ApiRequestParams()
                .setUrl(ApiUrl.USER_UPDATE)
                .addParam("userId", user.userId)
                .addParam("token", user.token)
                .addParam("userPwd", user.userPwd);

        NetworkResponse<UserResponse.User> res = NetKit.apiRequest().syncPost(params, new Parser<UserResponse.User>() {

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

                return response.data;

            }
        });

        if (res.hasError()) {
            ret.setErrorMsg(res.getErrorMsg());
            ret.code = res.httpCode;
            return ret;
        }
        ret.data = res.data;

        return ret;
    }
}
