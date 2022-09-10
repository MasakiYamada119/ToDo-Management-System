package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;

@Controller
public class MainController {
	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main")
	public String Calendar(Model model) {
		LocalDate firstday = LocalDate.now().withDayOfMonth(1); //その月の1日のLocalDates
	    DayOfWeek week = firstday.getDayOfWeek(); //曜日を表すDayOfWeek
	    LocalDate lastmonth = firstday.minusDays(week.getValue()); //当月カレンダーの前月分
	    LocalDate nextmonth = LocalDate.now().plusMonths(1L); //当月カレンダーの来月分
	    LocalDate lastDayOfNextMonth = nextmonth.withDayOfMonth(1);
	    final int lastday = firstday.lengthOfMonth(); //月末
	    
	    Tasks task = new Tasks();
         
		List<MultiValueMap<LocalDate, Tasks>> d = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
        	MultiValueMap<LocalDate, Tasks> d1 = new LinkedMultiValueMap<>(); //1週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d1.add(lastmonth, task);
            	d1.add(lastmonth.plusDays(1), task);
            	d1.add(lastmonth.plusDays(2), task);
            	d1.add(lastmonth.plusDays(3), task);
            	d1.add(firstday, task);
            	d1.add(firstday.plusDays(1), task);
            	d1.add(firstday.plusDays(2), task);
            }
            MultiValueMap<LocalDate, Tasks> d2 = new LinkedMultiValueMap<>(); //2週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d2.add(firstday.plusDays(3), task);
            	d2.add(firstday.plusDays(4), task);
            	d2.add(firstday.plusDays(5), task);
            	d2.add(firstday.plusDays(6), task);
            	d2.add(firstday.plusDays(7), task);
            	d2.add(firstday.plusDays(8), task);
            	d2.add(firstday.plusDays(9), task);
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            MultiValueMap<LocalDate, Tasks> d3 = new LinkedMultiValueMap<>(); //3週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d3.add(firstday.plusDays(10), task);
            	d3.add(firstday.plusDays(11), task);
            	d3.add(firstday.plusDays(12), task);
            	d3.add(firstday.plusDays(13), task);
            	d3.add(firstday.plusDays(14), task);
            	d3.add(firstday.plusDays(15), task);
            	d3.add(firstday.plusDays(16), task);
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            MultiValueMap<LocalDate, Tasks> d4 = new LinkedMultiValueMap<>(); //4週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d4.add(firstday.plusDays(17), task);
            	d4.add(firstday.plusDays(18), task);
            	d4.add(firstday.plusDays(19), task);
            	d4.add(firstday.plusDays(20), task);
            	d4.add(firstday.plusDays(21), task);
            	d4.add(firstday.plusDays(22), task);
            	d4.add(firstday.plusDays(23), task);
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println("\n");
    			}
            }
            MultiValueMap<LocalDate, Tasks> d5 = new LinkedMultiValueMap<>(); //5週目のLocalDateを格納するList
            for (int j = 1; j <= 1; j++) { 
            	d5.add(firstday.plusDays(24), task);
            	d5.add(firstday.plusDays(25), task);
            	d5.add(firstday.plusDays(26), task);
            	d5.add(firstday.plusDays(27), task);
            	d5.add(firstday.plusDays(28), task);
            	d5.add(firstday.plusDays(29), task);
            	d5.add(lastDayOfNextMonth, task);
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
	    model.addAttribute("tasks", d);
		model.addAttribute("matrix", d);
		return "main";
	}
}
