package org.horiga.study.test.eventprocessing.esper.subscriber;

import java.util.Map;

public interface UpdateSubscriber {
	
	void update(Map<String, Object> event);
}
