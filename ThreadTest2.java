public class ThreadTest2 extends Thread {
	private int threadNo;
	private String lock;

	public ThreadTest2(int threadNo, String lock) {
		this.threadNo = threadNo;
		this.lock = lock;
	}

	public static void main(String[] args) throws Exception {
		String lock1 = new String("lock");
		String lock2 = new String("lock2");

		new ThreadTest2(1, lock1).start();
		new ThreadTest2(2, lock2).start();
	}

	public void run() {
		synchronized (lock) {
			for (int i = 1; i < 10; i++) {
				System.out.println("No." + threadNo + ":" + i);
			}
		}
	}
}
