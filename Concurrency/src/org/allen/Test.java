package org.allen;

public class Test {
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
		/*System.out.println("main thread");
		new Thread(()-> System.out.println("start a new thread")).start();
		Thread.sleep(1000);
		System.out.println("main thread");*/
		
		/*for(int i=0; i<4; i++){
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){
				System.out.println("interrupted");
				return;
			}
			System.out.println(i);
		}*/
		
		/*System.out.println("main thread start");
		Thread t = new Thread(
			()->{
				for(int i=9; i > 5 ; i--){
					if(Thread.interrupted()) {
						//throw new InterruptedException();
						System.out.println("interrupted");
						return;
					}
					System.out.println(i);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(i == 7) Thread.currentThread().interrupt();
				}
			}
		);
		t.start();
		t.join();
		Thread.currentThread().interrupt();
		System.out.println("main thread end");
		System.out.println("main thread not interrupted");
		if(Thread.currentThread().isInterrupted()) {
			System.out.println("main thread finally interrupted");
			return;
		}
		System.out.println("main thread not interrupted");*/
		

		Counter c = new Counter();
		Thread t = new Thread(new SimpleThread(c));
		t.start();
		for(int i=0; i<100; i++){
			c.incr();
			System.out.println("i: "+c.getV());
			Thread.sleep(10);
		}
		System.out.println("--------------"+c.getV());
	}

}
