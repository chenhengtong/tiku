package com.tamguo.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.model.PaperEntity;
import com.tamguo.service.IAreaService;
import com.tamguo.service.ICourseService;
import com.tamguo.service.IPaperService;
import com.tamguo.service.IQuestionService;
import com.tamguo.util.PageUtils;
import com.tamguo.util.TamguoConstant;

/**
 * Controller - 试卷
 * 
 * @author candy.tam
 *
 */
@Controller
public class PaperController {
	
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private IAreaService iAreaService;
	@Autowired
	private IPaperService iPaperService;
	@Autowired
	private IQuestionService iQuestionService;

	@RequestMapping(value = {"/paperlist/{subjectId}/{courseId}-{paperType}-{year}-{area}-{pageNum}.html"}, method = RequestMethod.GET)
    public ModelAndView indexAction(@PathVariable String subjectId , @PathVariable String courseId , @PathVariable String paperType,
    		@PathVariable String year , @PathVariable String area , @PathVariable Integer pageNum, ModelAndView model) {
    	model.setViewName("paperlist");
    	model.addObject("courseList", iCourseService.findBySubjectId(subjectId));
    	model.addObject("areaList", iAreaService.findAll());
    	model.addObject("paperPage" , PageUtils.getPage(iPaperService.findList(courseId , paperType , year , area , pageNum)));
    	model.addObject("courseId", courseId);
    	model.addObject("paperType", paperType);
    	model.addObject("year", year);
    	model.addObject("area", area);
        return model;
    }
	
	@RequestMapping(value = {"/paper/{paperId}.html"}, method = RequestMethod.GET)
	public ModelAndView indexAction(@PathVariable String paperId , ModelAndView model){
		model.setViewName("paper");
		PaperEntity paper = iPaperService.find(paperId);
		model.addObject("paper", paper);
		model.addObject("questionNames", this.getQuestionTypeNames(paper.getQuestionType()));
		model.addObject("questionList", iQuestionService.findPaperQuestion(paperId));
		return model;
	}
	
	private String getQuestionTypeNames(String questionTypes){
		List<String> questionNames = new ArrayList<String>();
		if(StringUtils.isEmpty(questionTypes)){
			return StringUtils.EMPTY;
		}
		String[] types = questionTypes.split(",");
		for(String type : types){
			if(TamguoConstant.QUESTION_TYOE_DANXUANTI.equals(type)){
				questionNames.add("单选题");
			}else if(TamguoConstant.QUESTION_TYOE_JIANDATI.equals(type)){
				questionNames.add("简答题");
			}else if(TamguoConstant.QUESTION_TYOE_SHUMIANBIAODA.equals(type)){
				questionNames.add("书面表达");
			}
		}
		return StringUtils.join(questionNames, ",");
	}
	
}
