package DesignPattern;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ProducerAndConsumer2 {
	//Using await() and signal
	private int queueSize = 10;
	private Queue<Integer> q = new LinkedList<>();
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	public static void main(String[] args) {
		ProducerAndConsumer2 solution = new ProducerAndConsumer2();
		
		Producer p = solution.new Producer();
		Consumer c = solution.new Consumer();
		
		new Thread(p).start();
		new Thread(c).start();
	}
	
	class Producer implements Runnable{

		@Override
		public void run() {
			producer();
		}

		private void producer() {
			while(true) {
				lock.lock();
				try {
					if(q.size() == queueSize) {
						try {
							System.out.println("队列已满，请等待。。。");
							notFull.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					q.add(0);
					notEmpty.signal();
					System.out.println("队列中生产" + q.size() + "个产品");
				} finally {
					lock.unlock();
				}
			}
		}
		
	}
	
	class Consumer implements Runnable {

		@Override
		public void run() {
			consumer();
		}

		private void consumer() {
			while(true) {
				lock.lock();
				try {
					if(q.size() == 0) {
						try {
							System.out.println("队列已空， 请等待。。。");
							notEmpty.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					q.poll();
					notFull.signal();
					System.out.println("消费产品，剩余"+q.size()+"个");
				} finally {
					lock.unlock();
				}
			}
		}
		
	}
}
