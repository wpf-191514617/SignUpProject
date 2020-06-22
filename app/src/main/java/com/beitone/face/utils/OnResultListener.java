/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.beitone.face.utils;


import com.beitone.face.exception.FaceError;

public interface OnResultListener<T> {
    void onResult(T result);

    void onError(FaceError error);
}
