package com.lch.lottery.user.controller;

import com.lch.lottery.user.model.UserResponse;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class UserController {


    public interface Callback {

        void onSuccess(UserResponse.User data);

        void onFail(String msg);

    }

    public interface ClearCallback {

        void onSuccess();

        void onFail(String msg);

    }


    public void clearCurrentUser(final ClearCallback cb) {

//        NetKit.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                final ResponseValue res = localUserRepo.deleteCurrentUser();
//                if (res.err != null) {
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (cb != null) {
//                                cb.onFail(res.err.msg);
//                            }
//                        }
//                    });
//                    return;
//                }
//
//                NetKit.runInUI(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (cb != null) {
//                            cb.onSuccess();
//                        }
//                    }
//                });
//            }
//        });
    }

    public void getCurrentUser(final Callback callback) {
//        NetKit.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                final ResponseValue<UserResponse.User> res = localUserRepo.getCurrentUser();
//                if (res.err != null) {
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (callback != null) {
//                                callback.onFail(res.err.msg);
//                            }
//                        }
//                    });
//                    return;
//                }
//
//                NetKit.runInUI(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (callback != null) {
//                            callback.onSuccess(res.data);
//                        }
//                    }
//                });
//            }
//        });
    }


    public void login(final String username, final String pwd, final Callback callback) {
//        NetKit.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                final ResponseValue<UserResponse.User> res = remoteUserRepo.get(username, pwd);
//                if (res.err != null) {
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (callback != null) {
//                                callback.onFail(res.err.msg);
//                            }
//                        }
//                    });
//                    return;
//                }
//                if (res.data == null) {
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (callback != null) {
//                                callback.onFail("data is null");
//                            }
//                        }
//                    });
//                    return;
//                }
//
//                NetKit.runInUI(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (callback != null) {
//                            callback.onSuccess(res.data);
//                        }
//                    }
//                });
//            }
//        });
    }

    public void register(final UserResponse.User user, final Callback callback) {
//        NetKit.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                final ResponseValue<UserResponse.User> res = remoteUserRepo.add(user);
//                if (res.err != null) {
//
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (callback != null) {
//                                callback.onFail(res.err.msg);
//                            }
//                        }
//                    });
//                    return;
//                }
//                if (res.data == null) {
//                    NetKit.runInUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (callback != null) {
//                                callback.onFail("data is null");
//                            }
//                        }
//                    });
//                    return;
//                }
//
//                NetKit.runInUI(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (callback != null) {
//                            callback.onSuccess(res.data);
//                        }
//                    }
//                });
//            }
//        });
    }


}
