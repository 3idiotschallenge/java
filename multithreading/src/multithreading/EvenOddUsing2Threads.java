package multithreading;
/**
 * Even Thread will print even only if shared variable is even, otherwise this Thread will wait
 * Odd Thread will print Odd only if shared variable is odd, otherwise this thread will wait
 * 
 * @author Raghav
 *
 */
public class EvenOddUsing2Threads {
	
	static int range=1000;
	volatile static int currentCounter=0;
	
	public static void  print(){
		System.out.println(Thread.currentThread().getName()+" => "+currentCounter);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		Thread evenThread = new Thread(() -> {
			while (currentCounter <= range) {
				synchronized (EvenOddUsing2Threads.class) {
					if (currentCounter % 2 != 0) {
						try {
							EvenOddUsing2Threads.class.wait();
						} catch (InterruptedException e) {
						}
					} else {
						print();
						currentCounter++;
						EvenOddUsing2Threads.class.notifyAll();
					}
				}
			}
		});
		
		
		
		Thread oddThread = new Thread(() -> {
			while (currentCounter <= range) {
				synchronized (EvenOddUsing2Threads.class) {
					if (currentCounter % 2 == 0) {
						try {
							EvenOddUsing2Threads.class.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						print();
						currentCounter++;
						EvenOddUsing2Threads.class.notifyAll();
					}
				}
			}
		});
		
		evenThread.setName("Even Thread ");
		oddThread.setName("Odd Thread ");
		
		evenThread.start();
		oddThread.start();
	}

}

/*Sample O/p

Even Thread  => 0
Odd Thread  => 1
Even Thread  => 2
Odd Thread  => 3
Even Thread  => 4
Odd Thread  => 5
Even Thread  => 6
Odd Thread  => 7
Even Thread  => 8
Odd Thread  => 9
Even Thread  => 10
Odd Thread  => 11
.
.

*/
