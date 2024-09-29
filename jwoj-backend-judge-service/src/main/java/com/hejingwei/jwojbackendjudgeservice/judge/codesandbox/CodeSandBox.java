package com.hejingwei.jwojbackendjudgeservice.judge.codesandbox;


import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
