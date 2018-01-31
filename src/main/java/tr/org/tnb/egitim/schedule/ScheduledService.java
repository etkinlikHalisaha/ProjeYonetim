package tr.org.tnb.egitim.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
	
	private int num = 0;
	/*
	 *	fixedRate: metod cagirmalar arasindaki sure
	 *	fixedDelay: metod bittikten sonra tekrar cagrilmasi icin gecmesi gereken sure
	 *	cron: cronJob formatı
	 */
	// 0 0 * * * * = the top of every hour of every day.
	// */10 * * * * * = every ten seconds.
	// 0 0 8-10 * * * = 8, 9 and 10 o'clock of every day.
	// 0 0/30 8-10 * * * = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
	// 0 0 9-17 * * MON-FRI = on the hour nine-to-five weekdays
	// 0 0 0 25 12 ? = every Christmas Day at midnight
	@Scheduled(cron = "*/60 * * * * *")
	public void method() {
		System.out.println("Scheduled method: " + ++num);
	}
}
