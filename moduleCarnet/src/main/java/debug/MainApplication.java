package debug;

import com.yonyou.module.carnet.constant.Api;
import com.yonyou.sh.common.base.BaseApplication;
import com.yonyou.sh.common.bean.HttpResponse;
import com.yonyou.sh.common.constant.SPKeys;
import com.yonyou.sh.common.http.HttpCallback;
import com.yonyou.sh.common.http.HttpHelper;
import com.yonyou.sh.common.utils.ContextHelper;
import com.yonyou.sh.common.utils.JsonUtils;
import com.yonyou.sh.common.utils.LogUtils;
import com.yonyou.sh.common.utils.SPUtils;

import java.util.HashMap;

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    private void login() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", "18966668888");
        map.put("password", "123456");
        HttpHelper.getInstance().postJson(Api.ACCOUNT_LOGIN, map, null, new HttpCallback<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtils.e("==获取app的token", JsonUtils.getJsonStr(s, "jwt"));
                SPUtils.keepContent(ContextHelper.getContext(), SPKeys.SP_KEY_USER_TOKEN, JsonUtils.getJsonStr(s, "jwt"));
            }

            @Override
            public void onFailure(HttpResponse response) {

            }
        });
    }
}
