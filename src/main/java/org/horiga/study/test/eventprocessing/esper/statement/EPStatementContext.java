package org.horiga.study.test.eventprocessing.esper.statement;

import java.util.List;

import org.horiga.study.test.eventprocessing.esper.subscriber.UpdateSubscriber;

public interface EPStatementContext {
	
	String name();
	
	String epl();
	
	UpdateSubscriber subscriber();
	
	/**
	 * @see com.espertech.esper.client.UpdateListener
	 * @see com.espertech.esper.client.StatementAwareUpdateListener
	 */
	List<Object> listeners();
}
