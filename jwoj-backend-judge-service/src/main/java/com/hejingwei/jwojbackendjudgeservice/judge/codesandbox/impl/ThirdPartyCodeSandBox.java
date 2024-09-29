package com.hejingwei.jwojbackendjudgeservice.judge.codesandbox.impl;

import com.hejingwei.jwojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱(实际调用接口)
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
