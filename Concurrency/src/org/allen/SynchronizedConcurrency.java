package org.allen;

/**
 * 两个线程共同对一块内存地址的值作修改，会导致并发错误
 * @author Administrator
 *
 */
public class SynchronizedConcurrency {
	private static class Counter{
		private int c = 0;
		
		public synchronized int getV(){
			return c;
		}
		
		/**
		 * 相当于这个方法体都加上synchronized(this)
		 */
		public synchronized void incr(){
			c++;
			System.out.println("i: "+c);
		}
		
		public synchronized void decr(){
			c--;
			System.out.println("d: "+c);
		}
		
		/**
		 * 使用相同的lock的方法会一起同步
		 */
		public void incr2(){
			synchronized(this){
				c++; //执行这一步时，另一个线程的方法会等待，计算结果不会错误;
			}
			System.out.println("i: "+c); //执行这一步时，另一个线程的方法是可以工作的，导致显示错误
		}
		
		public void decr2(){
			synchronized(this){
				c--;
			}
			System.out.println("d: "+c);
		}
	}
	
	private static class MsLunch {
	    private long c1 = 0;
	    private long c2 = 0;
	    private Object lock1 = new Object();
	    private Object lock2 = new Object();
	    
	    /**
		 * 使用不同的lock的方法不会一起同步
		 */
	    public void inc1() {
	        synchronized(lock1) {
	            c1++;
	        }
	    }

	    public void inc2() {
	        synchronized(lock2) {
	            c2++;
	        }
	    }
	}
	
	private static class SimpleThread implements Runnable{
		
		private Counter c;
		
		public SimpleThread(Counter c){
			this.c = c;
		}
		
		@Override
		public void run() {
			for(int i=0; i<100; i++){
				c.decr2();
				//System.out.println("d: "+c.getV());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	

	public static void main(String[] args) throws InterruptedException {
		Counter c = new Counter();
		Thread t = new Thread(new SimpleThread(c));
		t.start();
		for(int i=0; i<100; i++){
			c.incr2();
			//System.out.println("i: "+c.getV());
			Thread.sleep(10);
		}
		System.out.println("--------------"+c.getV());
	}

}
