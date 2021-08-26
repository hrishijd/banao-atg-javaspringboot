package com.authentication.loginsystem;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SchedulerForLog {
	@PostConstruct
	@Scheduled(cron = "0 0 10 * * ?")
	public void printAtg()
	{
		log.info("HELLO ATG");
	}
}
