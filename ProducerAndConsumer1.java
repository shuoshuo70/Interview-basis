package DesignPattern;
import java.util.LinkedList;
import java.util.Queue;


public class ProducerAndConsumer1 {
	//Using wait() and notify
	private int queueSize = 10;
	private Queue<Integer> q = new LinkedList<>();
	
	public static void main(String[] args) {
		ProducerAndConsumer1 solution = new ProducerAndConsumer1();
		
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
				synchronized(q) {
					while(q.size() == queueSize) {
						try {
							System.out.println("������������ȴ�������");
							q.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							q.notify();
						}
					}
					
					q.add(0);
					q.notify();
					System.out.println("����������" + q.size() + "����Ʒ");
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
				synchronized(q) {
					if(q.size() == 0) {
						try {
							System.out.println("�����ѿգ� ��ȴ�������");
							q.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							q.notify();
						}
					}
					
					q.poll();
					q.notify();
					System.out.println("���Ѳ�Ʒ��ʣ��"+q.size()+"��");
				}
			}
		}
		
	}
}
