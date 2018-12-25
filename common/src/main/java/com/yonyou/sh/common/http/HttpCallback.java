package com.yonyou.sh.common.http;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.yonyou.sh.common.bean.HttpResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallback<Result> {

    public void onSucc(String result) {
        Gson gson = new Gson();
        Type type = getSuperClassType(getClass());
        if (type != null) {
            if (type.toString().contains("String")) {
                onSuccess((Result) result);
            } else {
                Result objResult = (Result) gson.fromJson(result, type);
                onSuccess(objResult);
            }
        } else {
            onSuccess((Result) result);
        }
    }

    private Type getSuperClassType(Class<?> subclass) {
        Type superclassType = subclass.getGenericSuperclass();
        if (superclassType instanceof Class) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclassType;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }


    public abstract void onSuccess(Result result);

    public abstract void onFailure(HttpResponse response);
}
