package com.dmm.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class EditController {
	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/edit")
	public String Edit(Model model) {
		List<Tasks> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("edit", list);
		TaskForm taskForm = new TaskForm();
		model.addAttribute("taskForm", taskForm);
		return "/edit";
	}
	
	@PostMapping("/main/edit")
	public String createpage(@Validated TaskForm taskForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (bindingResult.hasErrors()) {
			// エラーがある場合は投稿登録画面を返す
			List<Tasks> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			model.addAttribute("edit", list);
			model.addAttribute("taskForm", taskForm);
			return "/main";
		}
	
	    Tasks task = new Tasks();
	    task.setName(user.getName());
	    task.setTitle(taskForm.getTitle());
	    task.setText(taskForm.getText());
	    task.setDate(LocalDateTime.now());
	    task.setDone(task.getDone());

	    repo.save(task);

	    return "redirect:/main";
	}

	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		return "redirect:/main";
	}
}
