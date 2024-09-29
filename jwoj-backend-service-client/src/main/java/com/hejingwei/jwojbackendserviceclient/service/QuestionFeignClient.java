package com.hejingwei.jwojbackendserviceclient.service;


import com.hejingwei.ojbackendmodel.model.entity.Question;
import com.hejingwei.ojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author 86182
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-09-15 18:02:46
*/
@FeignClient(name = "jwoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient{

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") Long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId);

    @PostMapping("/question_submit/update")
    Boolean updateByQuestionSubmit(@RequestBody QuestionSubmit questionSubmit);

}
