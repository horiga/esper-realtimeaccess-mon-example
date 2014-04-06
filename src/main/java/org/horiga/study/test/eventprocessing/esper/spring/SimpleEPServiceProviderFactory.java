package org.horiga.study.test.eventprocessing.esper.spring;

import java.util.List;

import org.horiga.study.test.eventprocessing.esper.statement.EPStatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.UpdateListener;

public class SimpleEPServiceProviderFactory implements InitializingBean, FactoryBean<EPServiceProvider> {

	private static Logger log = LoggerFactory
			.getLogger(SimpleEPServiceProviderFactory.class);
	
	private EPServiceProvider _instance;
	
	protected Configuration configuration;
	protected String name = "default";
	protected String eventTypePackageName;
	protected List<EPStatementContext> statement;
	
	public EPServiceProvider getObject() throws Exception {
		return _instance;
	}

	public Class<?> getObjectType() {
		return EPServiceProvider.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		
		Assert.hasText(eventTypePackageName, "eventTypePackageName is must be specified");
		Assert.notEmpty(statement, "statement is empty");
		
		configuration = new Configuration();
		configuration.addEventTypeAutoName(eventTypePackageName);
		
		_instance = EPServiceProviderManager.getDefaultProvider(configuration);
		
		for (EPStatementContext stmt : statement) {
			final String epl = stmt.epl();
			log.info("EPL: {}", epl);
			EPStatement epstmt = _instance.getEPAdministrator().createEPL(epl);
			if (null != stmt.subscriber()) {
				epstmt.setSubscriber(stmt.subscriber());
			}
			
			if ( null != stmt.listeners()) {
				for (Object listener : stmt.listeners()) {
					if ( !(listener instanceof StatementAwareUpdateListener)
						&& !(listener instanceof UpdateListener)) {
						throw new IllegalStateException("statement listener illegal");
					}
					
					if ( listener instanceof StatementAwareUpdateListener) {
						epstmt.addListener((StatementAwareUpdateListener)listener);
					}
					
					if ( listener instanceof UpdateListener) {
						epstmt.addListener((UpdateListener)listener);
					}
					
					/*
					 * TODO: support addListenerWithReply
					 */
					//epstmt.addListenerWithReplay((UpdateListener)listener);
				}
			}
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEventTypePackageName(String eventTypePackageName) {
		this.eventTypePackageName = eventTypePackageName;
	}

	public void setStatement(List<EPStatementContext> statement) {
		this.statement = statement;
	}

}
