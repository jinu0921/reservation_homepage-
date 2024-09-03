package com.bitc.project.review.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitc.project.loginJoin.dto.loginJoinDTO;
import com.bitc.project.review.service.reviewServiceImpl;
import com.bitc.project.review.util.Criteria;
import com.bitc.project.review.util.PageMaker;
import com.bitc.project.review.vo.reviewVO;

import lombok.RequiredArgsConstructor;

@RequestMapping("review/")
@Controller
@RequiredArgsConstructor
public class reviewController {
	private final reviewServiceImpl rs;
	
	@GetMapping("review_list")
	public void review_list(Criteria cri, Model model) {
		List<reviewVO> rv =  rs.getReview(cri);
		PageMaker pm = rs.getPageMaker(cri);
		System.out.println("rv:"+rv);
		System.out.println("pm:"+pm);
		model.addAttribute("reviewList",rv);
		model.addAttribute("pm",pm);
	}
	
	@GetMapping("review_detail")
	public void review_detail(Model model, int review_num) {
		model.addAttribute("reviewDetail",rs.detailReview(review_num));
	}
	
	@GetMapping("review_write")
	public String G_review_write(HttpSession s) {
		if(s.getAttribute("member")!=null) {
			return "review/review_write";
		}else {
			System.out.println("로그인해라잉");
			return "redirect:/review/review_list";
		}
	}
	
	@PostMapping("review_write")
	public String P_review_write(reviewVO rv, HttpSession s) {
		System.out.println(rs.writeReview(rv));
		return "redirect:/review/review_list";
	}
	
	@GetMapping("review_update")
	public String G_review_update(int review_num, Model model, HttpSession s) {
		reviewVO rv = rs.findReviewNum(review_num);
		loginJoinDTO ldt =  (loginJoinDTO) s.getAttribute("member");
		if(ldt!=null) {
		if(rv.getMember_num() == ldt.getMember_num()) {
		model.addAttribute("reviewUpdate", rv);
		 return "review/review_update";
		}else {
			System.out.println("너작성자아니잖아");
			 return "redirect:/review/review_detail?review_num=" + review_num;
		}
		}else {
			System.out.println("너로그인안햇잖아");
			 return "redirect:/review/review_detail?review_num=" + review_num;
		}
	}
	
	@PostMapping("review_update")
	public String P_review_update(reviewVO rv) {
		System.out.println(rs.updateReview(rv));
		return "redirect:/review/review_list";
	}
	
	@PostMapping("review_delete")
	public String P_review_delete(int review_num, HttpSession s) {
		reviewVO rv = rs.findReviewNum(review_num);
		loginJoinDTO ldt =  (loginJoinDTO) s.getAttribute("member");
		if(ldt!=null) {
			if(rv.getMember_num() == ldt.getMember_num()) {
				System.out.println(rs.deleteReview(rv));
				return "redirect:/review/review_list";
			}else {
				System.out.println("너작성자아니잖아");
				 return "redirect:/review/review_detail?review_num=" + review_num;
			}
		}else {
			System.out.println("너로그인안햇잖아");
			 return "redirect:/review/review_detail?review_num=" + review_num;
		}
	}
}