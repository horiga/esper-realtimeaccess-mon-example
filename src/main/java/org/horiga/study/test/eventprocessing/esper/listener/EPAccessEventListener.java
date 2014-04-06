package org.horiga.study.test.eventprocessing.esper.listener;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.horiga.study.test.eventprocessing.esper.event.TimedRtaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.google.common.collect.Maps;

@Component
public class EPAccessEventListener implements StatementAwareUpdateListener {
	
	private static Logger log = LoggerFactory.getLogger(EPAccessEventListener.class);
	
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPServiceProvider epServiceProvider) {
		
		log.info(">>>>>>>>>>>>>> [handleUpdate]");
		
		/* newData */
		Map<String, AtomicLong> groupingAtApp = Maps.newLinkedHashMap();
		Map<String, AtomicLong> groupingAtOs = Maps.newLinkedHashMap();
		Map<String, AtomicLong> groupingAtRegion = Maps.newLinkedHashMap();
		
		for (EventBean newDataBean : newEvents) {
			
			String app = (String)newDataBean.get("app");
			String region = (String)newDataBean.get("region");
			String os = (String)newDataBean.get("os");
			Number num = (Number)newDataBean.get("val");
			String osKey = app + "#" + os;
			String regionKey = app + "#" + region;
			
			// insert RDBMS
			
			if ( groupingAtApp.containsKey(app)) {
				groupingAtApp.get(app).addAndGet(num.longValue());
			} else {
				groupingAtApp.put(app, new AtomicLong(num.longValue()));
			}
			
			if ( groupingAtOs.containsKey(osKey)) {
				groupingAtOs.get(osKey).addAndGet(num.longValue());
			} else {
				groupingAtOs.put(osKey, new AtomicLong(num.longValue()));
			}
			
			if ( groupingAtRegion.containsKey(regionKey)) {
				groupingAtRegion.get(regionKey).addAndGet(num.longValue());
			} else {
				groupingAtRegion.put(regionKey, new AtomicLong(num.longValue()));
			}
		}
		
		Date timestamp = new Date();
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, AtomicLong> entry : groupingAtApp.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue().get()).append(", ");
			
			/*
			 * sendEvent new RTA data.
			 */
			TimedRtaEvent evt = new TimedRtaEvent();
			evt.setApp(entry.getKey());
			evt.setAccess(entry.getValue().longValue());
			evt.setTimestamp(timestamp);
			
			epServiceProvider.getEPRuntime().sendEvent(evt);
		}
		log.info(">>>>>> access by app: {}", sb.toString());
		sb.setLength(0);
		
		for (Map.Entry<String, AtomicLong> entry : groupingAtOs.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue().get()).append(", ");
		}
		log.info(">>>>>> access by app&os: {}", sb.toString());
		sb.setLength(0);
		
		for (Map.Entry<String, AtomicLong> entry : groupingAtRegion.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue().get()).append(", ");
		}
		log.info(">>>>>> access by app&region: {}", sb.toString());
		
		log.info("<<<<<<<<<<<<<< [handleUpdate]");
	}
}
