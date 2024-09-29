package com.hejingwei.jwojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hejingwei.jwojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.hejingwei.jwojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.hejingwei.jwojbackendquestionservice.service.QuestionService;
import com.hejingwei.jwojbackendquestionservice.service.QuestionSubmitService;
import com.hejingwei.jwojbackendserviceclient.service.JudgeFeignClient;
import com.hejingwei.ojbackendcommon.common.ErrorCode;
import com.hejingwei.ojbackendcommon.constant.CommonConstant;
import com.hejingwei.ojbackendcommon.constant.UserConstant;
import com.hejingwei.ojbackendcommon.exception.BusinessException;
import com.hejingwei.ojbackendcommon.utils.SqlUtils;
import com.hejingwei.ojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hejingwei.ojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hejingwei.ojbackendmodel.model.entity.Question;
import com.hejingwei.ojbackendmodel.model.entity.QuestionSubmit;
import com.hejingwei.ojbackendmodel.model.entity.User;
import com.hejingwei.ojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.hejingwei.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.hejingwei.ojbackendmodel.model.vo.QuestionSubmitVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author 86182
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-09-15 18:06:25
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;
    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;


    /**
     * 提交问题答案
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 效验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 设置初始值
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(language);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setUserId(loginUser.getId());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 执行判题服务
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(questionSubmit.getId()));
        // CompletableFuture.runAsync(() -> {
        //    judgeFeignClient.doJudge(questionSubmit.getId());
        // });
        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类（用户根据哪些字段查询，根据前端传来的请求对象，得到MyBatis plus框架支持的查询QueryWrapper类）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long userId = questionSubmitQueryRequest.getUserId();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.like(status!=null, "status", status);
        queryWrapper.like(questionId!=null, "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交userId和登录用户Id不同）提交的代码
        if (!UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole()) && questionSubmitVO.getUserId() != loginUser.getId()){
            // 脱敏处理
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // List<QuestionSubmitVO> questionSubmitVOList = new ArrayList<>();
        // for (QuestionSubmit questionSubmit : questionSubmitList) {
        //     QuestionSubmitVO questionSubmitVO = this.getQuestionSubmitVO(questionSubmit, request);
        //     questionSubmitVOList.add(questionSubmitVO);
        // }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList
                .stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());

        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}




