package com.bitc.project.review.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class reviewVO {
		private int member_num;
		private int review_num;
		private String title;
		private String content;
		private LocalDateTime regdate;
		private int viewcnt;
		private int Rating;
		private String nickname;
	}