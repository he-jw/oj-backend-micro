package com.hejingwei.jwojbackendjudgeservice.controller.inner;

import com.hejingwei.jwojbackendjudgeservice.judge.JudgeService;
import com.hejingwei.jwojbackendserviceclient.service.JudgeFeignClient;
import com.hejingwei.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
