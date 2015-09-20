public class Book {

	//attributes of Book class
	String title, genre;
	Integer price, authorIndex, maxNumber;
	
	// constructor
	Book(String _title, String _genre, Integer _price, Integer _authorIndex, Integer _maxNumber) {
		title = _title;
		genre = _genre;
		price = _price;
		authorIndex = _authorIndex;
		maxNumber = _maxNumber;
	}

	// override the toString method
	@Override
	public String toString() {
		String book = title + ":" + genre + ":$" + price + ":" + authorIndex + ":" + maxNumber + "\n";
		return book;
	}
	
	public String[] convertArray() {
		String[] book = {title, genre, String.valueOf(price), String.valueOf(authorIndex), String.valueOf(maxNumber)};
		return book;
	}
}
