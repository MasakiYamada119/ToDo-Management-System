package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class MainController {
	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main")
	public String Calendar(@AuthenticationPrincipal AccountUserDetails user, Model model) {
		LocalDate firstday = LocalDate.now().withDayOfMonth(1); //今月の1日のLocalDates
	    DayOfWeek w = firstday.getDayOfWeek(); //曜日を表すDayOfWeek
	    LocalDate lastMonth = firstday.minusDays(w.getValue()); //当月カレンダーの前月分
	    LocalDate nextMonthAll = LocalDate.now().plusMonths(1L); //当月カレンダーの来月分(10月)
	    LocalDate nextMonthOfFirstday = nextMonthAll.withDayOfMonth(1);
	    LocalDate lastMonthAll = LocalDate.now().minusMonths(1L); //当月カレンダーの前月分(8月)
	    LocalDate LastMonthOfFirstday = lastMonthAll.withDayOfMonth(1);
	    final int lastDay = firstday.lengthOfMonth(); //月末
	    
	    MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
    	List<Tasks> list ;

    	//getAuthority(ユーザーに付与された権限を返すメソッド)を使いif文の条件式でstream処理(CollectionやListからデータを抽出する処理)を行い"ROLE_ADMIN"を判定する
    	//.map(GrantedAuthority::getAuthority)でstream<String>をstream<collection>(getAuthorityの戻り値)に変換する
    	if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"))) {
    	    list = repo.findAll(); //ROLE_ADMINならTasksの全情報を開示
    	} else {
    	    list = repo.findByDateBetween(firstday.atTime(0,0), nextMonthOfFirstday.atTime(23,59), user.getName());
    	}
    	for(Tasks t : list) {
    	    tasks.add(t.getDate().toLocalDate(), t);
    	}
         
		List<List<LocalDate>> matrix = new ArrayList<>();
            List<LocalDate> week = new ArrayList<>(); 
            // 1週目（前月分を含む1週目）
            for (int i = 1; i <= 7; i++) {
            	week.add(lastMonth);
            	lastMonth = lastMonth.plusDays(1);
            }
            // 2週目（2週目から月末まで）
            for(int i = 7; i <= lastDay; i++) {
                if(w == DayOfWeek.SATURDAY){
                    matrix.add(week);   // 月に週を追加
                    week = new ArrayList<>();  // 新しい週のListを作成
                }
                firstday = firstday.plusDays(1);
            }
    	model.addAttribute("tasks", tasks);
		model.addAttribute("matrix", matrix);
		model.addAttribute("prev", LastMonthOfFirstday);
		model.addAttribute("next", nextMonthOfFirstday);
		return "main";
	}
}
