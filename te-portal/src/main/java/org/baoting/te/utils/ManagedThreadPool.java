package org.baoting.te.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ManagedThreadPool {

	private static final Log logger = LogFactory.getLog(ManagedThreadPool.class);

	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors()*50;

	private static final ExecutorService threadpool = Executors.newFixedThreadPool(POOL_SIZE);

	private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(500);
	
	private static Thread jobThread = new Thread("job thread") {
		@Override
		public void run() {
			while (true) {
				try {
					Runnable task = queue.take();
					threadpool.execute(task);
				} catch (InterruptedException e) {
					logger.error(e, e);
				}
			}
		}
	};

	static {
		jobThread.setDaemon(true);
		jobThread.start();
	}

	public static void close() {
		threadpool.shutdown();
		jobThread.interrupt();
	}

	public static void addTask(Runnable task) {
		try {
			queue.put(task);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
	}
	
	public static <T> Future<T> submit(Callable<T> callable){
		return threadpool.submit(callable);
	}
}