package cn.bounter.common.model;

import java.util.concurrent.Future;

/**
 * 任务队列
 * 通过Spring的@Async模拟
 * 支持一次一线程的同步队列和多线程同时进行的异步队列
 * @author simon
 *
 */
public abstract class TaskQueue {

	public Future<?> async() {
		return execute();
	}


	public synchronized Future<?> sync() {
		return execute();
	}


	protected abstract Future<?> execute();

}
