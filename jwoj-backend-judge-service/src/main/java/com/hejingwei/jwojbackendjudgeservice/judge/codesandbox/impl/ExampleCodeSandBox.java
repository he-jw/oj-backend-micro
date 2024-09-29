package com.hejingwei.jwojbackendjudgeservice.judge.codesandbox.impl;

import com.hejingwei.jwojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.hejingwei.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.hejingwei.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.hejingwei.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

import static com.hejingwei.ojbackendmodel.model.enums.JudgeInfoMessageEnum.ACCEPTED;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
