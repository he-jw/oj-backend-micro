package com.hejingwei.jwojbackendjudgeservice.judge.codesandbox;

import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeRequest.toString());
        return executeCodeResponse;
    }
}

