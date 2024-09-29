package com.hejingwei.jwojbackendjudgeservice.judge.strategy;


import com.hejingwei.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.hejingwei.ojbackendmodel.model.entity.Question;
import com.hejingwei.ojbackendmodel.model.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> expectOutputList;

    private List<String> outputList;

    private Question question;

    private QuestionSubmit questionSubmit;


}
