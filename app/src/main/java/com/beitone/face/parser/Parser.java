/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.beitone.face.parser;


import com.beitone.face.exception.FaceError;

/**
 * JSON解析
 * @param <T>
 */
public interface Parser<T> {
    T parse(String json) throws FaceError;
}
