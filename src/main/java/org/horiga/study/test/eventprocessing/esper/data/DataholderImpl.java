package org.horiga.study.test.eventprocessing.esper.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class DataholderImpl<T extends Data> implements Dataholder<T> {
	
	private static final int DEFAULT_STOCK_CAPACITY = 100;
	
	private final BlockingQueue<Data> stock;
	
	private Data latestData;
	
	public DataholderImpl() {
		this.stock = new LinkedBlockingQueue<Data>(DEFAULT_STOCK_CAPACITY);
	}
	
	public DataholderImpl(int capacity) {
		this.stock = new LinkedBlockingQueue<Data>(capacity);
	}
	
	public synchronized void put(Data newData) throws Exception {
		Preconditions.checkNotNull(newData, "stock data is null");
		if (stock.size() >= stock.remainingCapacity())
			stock.take();
		stock.offer(newData);
		latestData = newData;
	}
	
	public List<Data> get(int count) {
		
		if ( stock.isEmpty()) 
			return new ArrayList<Data>(0);
		List<Data> out = Lists.newLinkedList();
		out.addAll(stock);
		
		int size = out.size();
		int fromIndex = size - count > 0 ? size - count : 0;
		int toIndex = fromIndex + count > size ? size : fromIndex + count;
		
		return out.size() < toIndex ? out : out.subList(fromIndex, toIndex);
	}
	
	public Data getLatestData() {
		return latestData;
	}
	
}
