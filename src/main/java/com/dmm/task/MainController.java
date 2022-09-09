package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	    LocalDate nextmonth = firstday.plusDays(week.getValue()); //当月カレンダーの来月分
	    final int lastDay = firstday.lengthOfMonth(); //月末
         
		List<List<LocalDate>> d = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<LocalDate> d1 = new ArrayList<>(); //1週目のLocalDateを格納するList
            for (int j = 0; j < 7; j++) { 
            	d1.add(lastmonth);
            	d1.add(lastmonth.plusDays(1));
            	d1.add(lastmonth.plusDays(2));
            	d1.add(lastmonth.plusDays(3));
            	d1.add(firstday);
            	d1.add(firstday.plusDays(1));
            	d1.add(firstday.plusDays(2));
            }
            List<LocalDate> d2 = new ArrayList<>(); //2週目のLocalDateを格納するList
            for (int j = 0; j < lastDay; j++) { 
            	d2.add(firstday.plusDays(3));
            	d2.add(firstday.plusDays(4));
            	d2.add(firstday.plusDays(5));
            	d2.add(firstday.plusDays(6));
            	d2.add(firstday.plusDays(7));
            	d2.add(firstday.plusDays(8));
            	d2.add(firstday.plusDays(9));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println();
    			}
            }
            List<LocalDate> d3 = new ArrayList<>(); //3週目のLocalDateを格納するList
            for (int j = 0; j < lastDay; j++) { 
            	d2.add(firstday.plusDays(10));
            	d2.add(firstday.plusDays(11));
            	d2.add(firstday.plusDays(12));
            	d2.add(firstday.plusDays(13));
            	d2.add(firstday.plusDays(14));
            	d2.add(firstday.plusDays(15));
            	d2.add(firstday.plusDays(16));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println();
    			}
            }
            List<LocalDate> d4 = new ArrayList<>(); //4週目のLocalDateを格納するList
            for (int j = 0; j < lastDay; j++) { 
            	d2.add(firstday.plusDays(17));
            	d2.add(firstday.plusDays(18));
            	d2.add(firstday.plusDays(19));
            	d2.add(firstday.plusDays(20));
            	d2.add(firstday.plusDays(21));
            	d2.add(firstday.plusDays(22));
            	d2.add(firstday.plusDays(23));
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println();
    			}
            }
            List<LocalDate> d5 = new ArrayList<>(); //5週目のLocalDateを格納するList
            for (int j = 0; j < lastDay; j++) { 
            	d2.add(firstday.plusDays(24));
            	d2.add(firstday.plusDays(25));
            	d2.add(firstday.plusDays(26));
            	d2.add(firstday.plusDays(27));
            	d2.add(firstday.plusDays(28));
            	d2.add(firstday.plusDays(29));
            	d2.add(nextmonth);
            	if (week == DayOfWeek.SATURDAY) {
            		System.out.println();
    			}
            }
            d.add(d1);
            d.add(d2);
            d.add(d3);
            d.add(d4);
            d.add(d5);
        }
		model.addAttribute("matrix", d);
		return "main";
	}

}
