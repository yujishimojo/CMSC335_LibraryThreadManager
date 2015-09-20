import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.*;

public class LibraryServer {
	// Create a master book index list
	static List<Integer> masterBookIndices = Collections.synchronizedList(new ArrayList<Integer>());
	// Maximum master size
	static int CAPACITY = 50;
	// Create a new lock
	static Lock lock = new ReentrantLock();
	// Create two conditions
	static Condition notEmpty = lock.newCondition();
	static Condition notFull = lock.newCondition();
	// Create a master book list
	static List<Book> masterBooks = Collections.synchronizedList(new ArrayList<Book>());

	// Constructor of LibraryServer
	public LibraryServer() {
		// create instances of books
		masterBooks.add(new Book("The Lost Symbol", "Mystery & Thrillers", 10, 0, 2));
		masterBooks.add(new Book("Angels & Demons", "Mystery & Thrillers", 16, 0, 3));
		masterBooks.add(new Book("The Da Vinci Code", "Mystery & Thrillers", 10, 0, 4));
		masterBooks.add(new Book("Deception Point", "Mystery & Thrillers", 16, 0, 2));
		masterBooks.add(new Book("Digital Fortress", "Mystery & Thrillers", 9, 0, 3));
		masterBooks.add(new Book("The 7 Habits of Highly Effective People", "Business & Investing", 16, 1, 2));
		masterBooks.add(new Book("The 8th Habit: From Effectiveness to Greatness", "Business & Investing", 16, 1, 3));
		masterBooks.add(new Book("The 3rd Alternative: Solving Life's Most Difficult Problems", "Business & Investing", 16, 1, 2));
		masterBooks.add(new Book("What I Wish I Knew When I Was 20", "Business & Investing", 23, 2, 3));
		masterBooks.add(new Book("inGenius: A Crash Course on Creativity", "Business & Investing", 26, 2, 2));		
		masterBooks.add(new Book("Introduction to Java Programming, Comprehensive", "Computers & Technology", 129, 3, 2));
		masterBooks.add(new Book("Java Concurrency in Practice", "Computers & Technology", 60, 4, 4));
		masterBooks.add(new Book("English Grammar In Use", "Education & Reference", 36, 5, 3));
		masterBooks.add(new Book("Steve Jobs", "Biographies & Memoirs", 30, 6, 3));
		masterBooks.add(new Book("Einstein: His Life and Universe", "Biographies & Memoirs", 19, 6, 2));
		masterBooks.add(new Book("Benjamin Franklin: An American Life", "Biographies & Memoirs", 19, 6, 2));
		masterBooks.add(new Book("Kissinger: A Biography", "Biographies & Memoirs", 22, 6, 3));	
		
		// Add book indices to the master index list
		for (int i=0; i<masterBooks.size(); i++) {
			masterBookIndices.add(i);
		}
		// Copy each object indices specified times by the fifth field
		for (int i=0; i<masterBooks.size(); i++) {
			int n = masterBooks.get(i).maxNumber;
			for (int m=0; m<n-1; m++) {
				masterBookIndices.add(i);				
			}
		}
	}
	// Method for showing books server holds
	public static ArrayList<Book> showLibraryServerBooks() {
		ArrayList<Book> array = new ArrayList<Book>();
		for (int i=0; i<masterBookIndices.size(); i++) {
			array.add(masterBooks.get(masterBookIndices.get(i)));
		}
		return array;
	}
}
