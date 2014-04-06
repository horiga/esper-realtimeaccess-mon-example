package org.horiga.study.test.eventprocessing.esper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPServiceProvider;

@Component
public class EsperAccessEventHandler {

	static Logger log = LoggerFactory.getLogger(EsperAccessEventHandler.class);
	
	@Autowired
	@Qualifier("epServiceProvider")
	protected EPServiceProvider provider;
	
	public void handleEvent(Object event) {
		provider.getEPRuntime().sendEvent(event);
	}
	
}
