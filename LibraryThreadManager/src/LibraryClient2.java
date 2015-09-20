import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryClient2 implements Runnable {
	
	static List<Integer> borrowedBookIndices2 = Collections.synchronizedList(new ArrayList<Integer>());
	private static int value, index;
		
	public int borrowBook() {
		synchronized (LibraryServer.lock) {
			LibraryServer.lock.lock(); // Acquire the lock
			try {
				if (LibraryServer.masterBookIndices.isEmpty()) {
					System.out.println("\t\t\t\tWait for notEmpty condition");
					((Thread) LibraryServer.notEmpty).interrupt();
				} else {
					Collections.shuffle(LibraryServer.masterBookIndices);
					value = LibraryServer.masterBookIndices.get(0);
					borrowedBookIndices2.add(value);
					index = LibraryServer.masterBookIndices.indexOf(value);
					LibraryServer.masterBookIndices.remove(index);
				}
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} finally {
				LibraryServer.lock.unlock(); // Release the lock	
				return value;
			}
		}
	}
	public int returnBook() {
		synchronized (LibraryServer.lock) {
			LibraryServer.lock.lock(); // Acquire the lock
			try {
				if (borrowedBookIndices2.isEmpty()) {
					System.out.println("\t\t\t\tWait for notEmpty condition");
					((Thread) LibraryServer.notEmpty).interrupt();
				} else if (LibraryServer.masterBookIndices.size() == LibraryServer.CAPACITY) {
					System.out.println("Wait for notFull condition");
					((Thread) LibraryServer.notFull).interrupt();
				} else {
					Collections.shuffle(borrowedBookIndices2);
					value = borrowedBookIndices2.get(0);
					LibraryServer.masterBookIndices.add(value);
					index = borrowedBookIndices2.indexOf(value);
					borrowedBookIndices2.remove(index);
				}
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} finally {
				LibraryServer.lock.unlock(); // Release the lock
				return value;
			}
		}
	}
	
	public void run() {
		try {
			for (int i=0; i<40; i++) {
				int num = (int)(Math.random()*(2));
				if (num == 0) {
					System.out.println("\t\t\t\tClient2 borrows book index " + borrowBook());
				} else if (num == 1) {
					System.out.println("\t\t\t\tClient2 returns book index " + returnBook());
				}					
				Thread.sleep((int)(Math.random() * 1000));	
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	// Method for showing books client2 holds
	public static ArrayList<Book> showClient2Books() {
		ArrayList<Book> array = new ArrayList<Book>();
		for (int i=0; i<borrowedBookIndices2.size(); i++) {
			array.add(LibraryServer.masterBooks.get(borrowedBookIndices2.get(i)));
		}
		return array;
	}
}
