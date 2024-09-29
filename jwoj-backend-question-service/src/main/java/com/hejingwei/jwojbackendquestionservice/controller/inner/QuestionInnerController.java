package com.hejingwei.jwojbackendquestionservice.controller.inner;


import com.hejingwei.jwojbackendquestionservice.service.QuestionService;
import com.hejingwei.jwojbackendquestionservice.service.QuestionSubmitService;
import com.hejingwei.jwojbackendserviceclient.service.QuestionFeignClient;
import com.hejingwei.ojbackendmodel.model.entity.Question;
import com.hejingwei.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") Long questionId){
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId){
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public Boolean updateByQuestionSubmit(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

}
