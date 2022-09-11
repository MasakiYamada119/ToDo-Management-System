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
		LocalDate firstday = LocalDate.now().withDayOfMonth(1); //その月の1日のLocalDates
	    DayOfWeek week = firstday.getDayOfWeek(); //曜日を表すDayOfWeek
	    LocalDate lastmonth = firstday.minusDays(week.getValue()); //当月カレンダーの前月分
	    LocalDate nextmonth = LocalDate.now().plusMonths(1L); //当月カレンダーの来月分
	    LocalDate lastDayOfNextMonth = nextmonth.withDayOfMonth(1);
	    final int lastday = firstday.lengthOfMonth(); //月末
	    LocalDate start = LocalDate.now();
	    LocalDate end = LocalDate.now();
	    
	    MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
    	List<Tasks> list ;

    	//getAuthority(ユーザーに付与された権限を返すメソッド)を使いif文の条件式でstream処理(CollectionやListからデータを抽出する処理)を行い"ROLE_ADMIN"を判定する
    	//.map(GrantedAuthority::getAuthority)でstream<String>をstream<collection>(getAuthorityの戻り値)に変換する
    	if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"))) {
    	    list = repo.findAll(); //ROLE_ADMINならTasksの全情報を開示
    	} else {
    	    list = repo.findByDateBetween(start.atTime(9,1), end.atTime(9,30), user.getName());
    	}
    	for(Tasks t : list) {
    	    tasks.add(t.getDate().toLocalDate(), t);
    	}
         
		List<List<LocalDate>> d = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            List<LocalDate> d1 = new ArrayList<>(); //1週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d1.add(lastmonth);
            	d1.add(lastmonth.plusDays(1));
            	d1.add(lastmonth.plusDays(2));
            	d1.add(lastmonth.plusDays(3));
            	d1.add(firstday);
            	d1.add(firstday.plusDays(1));
            	d1.add(firstday.plusDays(2));
            }
            List<LocalDate> d2 = new ArrayList<>(); //2週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d2.add(firstday.plusDays(3));
            	d2.add(firstday.plusDays(4));
            	d2.add(firstday.plusDays(5));
            	d2.add(firstday.plusDays(6));
            	d2.add(firstday.plusDays(7));
            	d2.add(firstday.plusDays(8));
            	d2.add(firstday.plusDays(9));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            List<LocalDate> d3 = new ArrayList<>(); //3週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d3.add(firstday.plusDays(10));
            	d3.add(firstday.plusDays(11));
            	d3.add(firstday.plusDays(12));
            	d3.add(firstday.plusDays(13));
            	d3.add(firstday.plusDays(14));
            	d3.add(firstday.plusDays(15));
            	d3.add(firstday.plusDays(16));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            List<LocalDate> d4 = new ArrayList<>(); //4週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d4.add(firstday.plusDays(17));
            	d4.add(firstday.plusDays(18));
            	d4.add(firstday.plusDays(19));
            	d4.add(firstday.plusDays(20));
            	d4.add(firstday.plusDays(21));
            	d4.add(firstday.plusDays(22));
            	d4.add(firstday.plusDays(23));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            List<LocalDate> d5 = new ArrayList<>(); //5週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d5.add(firstday.plusDays(24));
            	d5.add(firstday.plusDays(25));
            	d5.add(firstday.plusDays(26));
            	d5.add(firstday.plusDays(27));
            	d5.add(firstday.plusDays(28));
            	d5.add(firstday.plusDays(29));
            	d5.add(lastDayOfNextMonth);
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            d.add(d1);
            d.add(d2);
            d.add(d3);
            d.add(d4);
            d.add(d5);
        }
    	model.addAttribute("tasks", tasks);
		model.addAttribute("matrix", d);
		return "main";
	}

}
