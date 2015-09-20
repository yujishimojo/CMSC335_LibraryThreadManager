import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryClient1 implements Runnable {
	
	static List<Integer> borrowedBookIndices1 = Collections.synchronizedList(new ArrayList<Integer>());
	private static int value, index;
	
	public int borrowBook() {
		synchronized (LibraryServer.lock) {
			LibraryServer.lock.lock(); // Acquire the lock
			try {
				if (LibraryServer.masterBookIndices.isEmpty()) {
					System.out.println("\t\t\tWait for notEmpty condition");
					((Thread) LibraryServer.notEmpty).interrupt();
				} else {
					Collections.shuffle(LibraryServer.masterBookIndices);
					value = LibraryServer.masterBookIndices.get(0);
					borrowedBookIndices1.add(value);
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
				if (borrowedBookIndices1.isEmpty()) {
					System.out.println("Wait for notEmpty condition");
					((Thread) LibraryServer.notEmpty).interrupt();
				} else if (LibraryServer.masterBookIndices.size() == LibraryServer.CAPACITY) {
					System.out.println("Wait for notFull condition");
					((Thread) LibraryServer.notFull).interrupt();
				} else {
					Collections.shuffle(borrowedBookIndices1);
					value = borrowedBookIndices1.get(0);
					LibraryServer.masterBookIndices.add(value);
					index = borrowedBookIndices1.indexOf(value);
					borrowedBookIndices1.remove(index);
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
					System.out.println("Client1 borrows book index " + borrowBook());

				} else if (num == 1) {
					System.out.println("Client1 returns book index " + returnBook());
				}					
				Thread.sleep((int)(Math.random() * 1000));
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	// Method for showing books clients holds
	public static ArrayList<Book> showClient1Books() {
		ArrayList<Book> array = new ArrayList<Book>();
		for (int i=0; i<borrowedBookIndices1.size(); i++) {
			array.add(LibraryServer.masterBooks.get(borrowedBookIndices1.get(i)));
		}
		return array;
	}
}
