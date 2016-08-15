package org.allen;

/**
 * �����̹߳�ͬ��һ���ڴ��ַ��ֵ���޸ģ��ᵼ�²�������
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
		 * �൱����������嶼����synchronized(this)
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
		 * ʹ����ͬ��lock�ķ�����һ��ͬ��
		 */
		public void incr2(){
			synchronized(this){
				c++; //ִ����һ��ʱ����һ���̵߳ķ�����ȴ����������������;
			}
			System.out.println("i: "+c); //ִ����һ��ʱ����һ���̵߳ķ����ǿ��Թ����ģ�������ʾ����
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
		 * ʹ�ò�ͬ��lock�ķ�������һ��ͬ��
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
