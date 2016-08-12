package org.allen;

/**
 * 两个线程共同对一块内存地址的值作修改，会导致并发错误
 * @author Administrator
 *
 */
public class ConcurrencyError {
	private static class Counter{
		private int c = 0;
		
		public int getV(){
			return c;
		}
		
		public void incr(){
			c++;
		}
		
		public void decr(){
			c--;
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
				c.decr();
				System.out.println("d: "+c.getV());
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
			c.incr();
			System.out.println("i: "+c.getV());
			Thread.sleep(10);
		}
		System.out.println("--------------"+c.getV()); //the result could be 0, -1, 1 or even 2 and -2;
	}

}
