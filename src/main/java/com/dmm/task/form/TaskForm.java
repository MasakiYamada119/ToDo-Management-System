package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskForm {
	@Size(min = 1, max = 200)
	private String title;
	
	@Size(min = 1, max = 200)
	private String text;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")    // データフォーマットを指定する
	private LocalDate date;
	
	private Boolean done;

}
