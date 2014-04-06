package org.horiga.study.test.eventprocessing.esper;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.math.RandomUtils;
import org.horiga.study.test.eventprocessing.esper.event.AccessEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;


public class EsperTestMain {
	
	private static ClassPathXmlApplicationContext ctx;
	
	public void run() {
		
		final long testThreshold = 100000;
    	final List<String> regions = Lists.newArrayList("JP", "US", "TW", "TH", "FR");
		final List<String> os = Lists.newArrayList("ios", "android");
		final List<String> apps = Lists.newArrayList("GameApp", "NewsApp", "MessApp", "MailApp");
		final List<String> userHashes = Lists.newArrayList();
		for (int i = 0; i < 10000; i++) {
			userHashes.add(UUID.randomUUID().toString());
		}
		
		final EsperAccessEventHandler esper = (EsperAccessEventHandler)ctx.getBean("esperAccessEventHandler");
		
		ExecutorService tester = Executors.newSingleThreadExecutor();
		tester.submit(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				long count = 0;
				while (count < testThreshold) {
					AccessEvent event = new AccessEvent();
					
					event.setApp(apps.get(RandomUtils.nextInt(apps.size())));
					event.setVersion("1.0");
					event.setOs(os.get(RandomUtils.nextInt(os.size())));
					event.setUserHash(userHashes.get(RandomUtils.nextInt(userHashes.size())));
					event.setRegion(regions.get(RandomUtils.nextInt(regions.size())));
					
					esper.handleEvent(event);
					
					count++;
					if ( count % 128 == 0) System.out.println(""); else System.out.print(".");
					
					try {
						Thread.currentThread().sleep(10L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public static void main(String[] args) {
		
		ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		new EsperTestMain().run();
		
	}
}
