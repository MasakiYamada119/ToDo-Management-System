package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	public String Calendar(@AuthenticationPrincipal AccountUserDetails user,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, Model model) {
		
		List<List<LocalDate>> matrix = new ArrayList<>(); //週が入る行を作成するリスト
        List<LocalDate> week = new ArrayList<>(); //週のLocalDateを格納するリスト(自動的にmatrixに格納される)
		
		LocalDate day;
		if(date == null) {
            // 引数のdateがnullだったら、今月と判断する ⇒ 今月のカレンダーを表示
            day = LocalDate.now();
            day = LocalDate.of(day.getYear(), day.getMonthValue(), 1);
        }else {
            // dateに値が入っていたら（①②の値が渡ってくる）、前月か来月の日付が入っている ⇒ 前月か来月のカレンダーを表示
            day = date;
        }
		
		model.addAttribute("prev", day.minusMonths(1));
		model.addAttribute("next", day.plusMonths(1));
		
        DayOfWeek wf = day.getDayOfWeek(); //1日の曜日を表すDayOfWeek
        day = day.minusDays(wf.getValue()); //今月カレンダーの前月分
		
        // 1週目（前月分を含む1週目）
        for (int i = 1; i <= 7; i++) {
        	wf = day.getDayOfWeek();
            week.add(day);
            day = day.plusDays(1);
            if(wf == DayOfWeek.SATURDAY){
                matrix.add(week);   // 月に週を追加
                week = new ArrayList<>();  // 新しい週のListを作成
            }
        }
        
        // 2週目（2週目から月末まで）
        int leftOfMonth = day.lengthOfMonth() - day.getDayOfMonth();    // ①1週目の当月分
        leftOfMonth = day.lengthOfMonth() - leftOfMonth;    // ②当月の全日数から①を引く
        leftOfMonth = 7 - leftOfMonth;    // ③int i = 7を考慮した追加日数
        
        for(int i = 7; i <= day.lengthOfMonth() + leftOfMonth; i++) {
        	wf = day.getDayOfWeek();
            week.add(day);
            day = day.plusDays(1);
            if(wf == DayOfWeek.SATURDAY){
                matrix.add(week);   // 月に週を追加
                week = new ArrayList<>();  // 新しい週のListを作成
            }
        }
        
        // 最終週（来月の一週目）
        wf = day.getDayOfWeek();
        for (int i = 1; i <= 7 - wf.getValue(); i++) {
            week.add(day);
            day = day.plusDays(1);
        }
        matrix.add(week);   // 月に週を追加
        
        MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
        List<Tasks> list ;
        
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);

        //getAuthority(ユーザーに付与された権限を返すメソッド)を使いif文の条件式でstream処理(CollectionやListからデータを抽出する処理)を行い"ROLE_ADMIN"を判定する
        //.map(GrantedAuthority::getAuthority)でstream<String>をstream<collection>(getAuthorityの戻り値)に変換する
        if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"))) {
        	 list = repo.findByDateBetweenAdmin(firstDay.atTime(0,0), firstDay.plusMonths(1L).atTime(0,0)); //ROLE_ADMINならTasksの全情報を開示
        } else {
        	 list = repo.findByDateBetweenUser(firstDay.atTime(0,0), firstDay.plusMonths(1L).atTime(0,0), user.getName());
        }
        for(Tasks t : list) {
        	 tasks.add(t.getDate().toLocalDate(), t);
        }
        
    	model.addAttribute("tasks", tasks);
		model.addAttribute("matrix", matrix);
		return "main";
	}
}
