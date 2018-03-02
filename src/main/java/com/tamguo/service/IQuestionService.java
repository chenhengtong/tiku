package com.tamguo.service;

import com.github.pagehelper.Page;
import com.tamguo.model.QuestionEntity;

public interface IQuestionService {

	/** 根据章节获取问题 */
	public Page<QuestionEntity> findByChapterId(String chapterId ,  Integer offset ,  Integer limit);
	
	public QuestionEntity findById(String uid);
	
}
