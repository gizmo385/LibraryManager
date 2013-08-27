package com.gizmo385.lm.gui.dialogs;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gizmo385.lm.ItemLibrary;
import com.gizmo385.lm.types.Album;
import com.gizmo385.lm.types.Book;
import com.gizmo385.lm.types.Movie;
import com.gizmo385.lm.types.VideoGame;

/**
 * This dialog is used to add items to the library.
 * 
 * @author Christopher
 *
 */
public class AddItemDialog extends JDialog implements ActionListener {
	ItemLibrary lib;

	//Item types
	private final String BOOK = "book";
	private final String MOVIE = "movie";
	private final String ALBUM = "album";
	private final String VIDEOGAME = "videogame";

	//addBook components
	private JTextField bookName, bookGenre, bookAuthor, bookId, bookTags, bookCopies, bookPublisher, bookYearPublished;
	private JButton submitBook, cancelBook;

	//addMovie components
	private JTextField movieName, movieGenre, movieId, movieTags, movieCopies, movieDirector, movieRating, movieStars, movieYearReleased;
	private JButton submitMovie, cancelMovie;

	//addVideoGame components
	private JTextField videoGameName, videoGameGenre, videoGameId, videoGameTags, videoGameConsole, videoGameDeveloper, videoGamePublisher, videoGameContentRating, videoGameCopies;
	private JButton submitVideoGame, cancelVideoGame;

	//addAlbum components
	private JTextField albumName, albumGenre, albumId, albumTags, albumArtist, albumSongList, albumLabel, albumYearReleased, albumCopies;
	private JButton submitAlbum, cancelAlbum;

	//Panels
	private JPanel addBook, addMovie, addVideoGame, addAlbum, cards;

	//Layouts
	private CardLayout cardLayout;

	//Final variables
	private static final long serialVersionUID = -1177073111608428671L;
	private static final int WIDTH = 320;
	private static final int HEIGHT = 315;

	public AddItemDialog( Frame parent, boolean modality, ItemLibrary lib, String itemType ) {
		super( parent, "Add Item", modality );

		this.lib = lib;
		init( itemType );

		super.setSize( WIDTH, HEIGHT );
		super.setLayout( new FlowLayout() );
		super.setLocationRelativeTo( parent );
		super.setVisible( true );
		super.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}

	/**
	 * Manage button actions
	 */
	public void actionPerformed( ActionEvent ae ) {
		Object o = ae.getSource();

		if( o == this.cancelVideoGame || o == this.cancelBook || o == this.cancelMovie || o == this.cancelAlbum ) {
			dispose();
		}
		else if( o == this.submitBook ) {
			//Get input
			try {
				String title = this.bookName.getText();
				String author = this.bookAuthor.getText();
				String genre = this.bookGenre.getText();
				String isbn = this.bookId.getText();
				String tagline = this.bookTags.getText();
				int copies = Integer.parseInt( this.bookCopies.getText() );
				String publisher = this.bookPublisher.getText();
				String yearPublished = this.bookYearPublished.getText();

				ArrayList<String> tags = parseTagline( tagline );

				//Validate input, add Book, save library, and close dialog
				if( validateYear( yearPublished) && copies >= 0 ) {
					lib.add( new Book( title, genre, isbn, copies, tags, author,  publisher, Integer.parseInt( yearPublished ) ) );
					lib.save();
					JOptionPane.showMessageDialog( this, "Successfully added book to library!", "Saved book successfully", JOptionPane.INFORMATION_MESSAGE );
					dispose();
				}
				else
					JOptionPane.showMessageDialog( this, "Please ensure that your copies field and that your 4-digit year is valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
			} 
			catch ( NumberFormatException nfe ) {
				JOptionPane.showMessageDialog( this, "Please check that the numbers you have entered are valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( nfe );
			} 
			catch ( NullPointerException npe ) {
				JOptionPane.showMessageDialog( this, "Please check that all fields are filled out correctly.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( npe );
			}
		}
		else if( o == this.submitVideoGame ) {
			//Get input
			try {
				String title = this.videoGameName.getText();
				String genre = this.videoGameGenre.getText();
				String id = this.videoGameId.getText();
				String tagline = this.videoGameTags.getText();
				String console = this.videoGameConsole.getText();
				String developer = this.videoGameDeveloper.getText();
				String publisher = this.videoGamePublisher.getText();
				String contentRating = this.videoGameContentRating.getText();
				int copies = Integer.parseInt( this.videoGameCopies.getText() );

				ArrayList<String> tags = parseTagline( tagline );

				//Validate input, add VideoGame, save library, and close dialog
				if( copies >= 0 ) {
					lib.add( new VideoGame( title, genre, id, copies, tags, developer, publisher, console, contentRating ) );
					lib.save();
					JOptionPane.showMessageDialog( this, "Successfully added video game to library!", "Saved video game successfully", JOptionPane.INFORMATION_MESSAGE );
					dispose();
				}
				else
					JOptionPane.showMessageDialog( this, "Please ensure that your copies field is valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
			}
			catch ( NumberFormatException nfe ) {
				JOptionPane.showMessageDialog( this, "Please check that the numbers you have entered are valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( nfe );
			} 
			catch ( NullPointerException npe ) {
				JOptionPane.showMessageDialog( this, "Please check that all fields are filled out correctly.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( npe );
			}
		}

		else if( o == this.submitMovie ) {
			//Get input
			try {
				String title = this.movieName.getText();
				String genre = this.movieGenre.getText();
				String id = movieId.getText();
				String tagline = movieTags.getText();
				int copies = Integer.parseInt( movieCopies.getText() );
				String director = movieDirector.getText();
				String rating = movieRating.getText();
				String starline = movieStars.getText();
				String yearReleased = movieYearReleased.getText();

				ArrayList<String> tags = parseTagline( tagline );
				ArrayList<String> stars = parseTagline( starline );

				//Validate input, add Movie, save library, and close dialog
				if(  validateYear( yearReleased ) && copies >= 0 ) {
					lib.add( new Movie( title, genre, id, copies, tags, director, rating, Integer.parseInt( yearReleased ), stars ) );
					lib.save();
					JOptionPane.showMessageDialog( this, "Successfully added movie to library!", "Saved movie successfully", JOptionPane.INFORMATION_MESSAGE );
					dispose();
				}
				else
					JOptionPane.showMessageDialog( this, "Please ensure that your copies field and that your 4-digit year is valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
			}
			catch ( NumberFormatException nfe ) {
				JOptionPane.showMessageDialog( this, "Please check that the numbers you have entered are valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( nfe );
			} 
			catch ( NullPointerException npe ) {
				JOptionPane.showMessageDialog( this, "Please check that all fields are filled out correctly.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( npe );
			}
		}
		else if( o == this.submitAlbum ) {
			//Get input
			try {
				String name = albumName.getText();
				String genre = albumGenre.getText();
				String id = albumId.getText();
				String tagline = albumTags.getText();
				String artist = albumArtist.getText();
				String songline = albumSongList.getText();
				String label = albumLabel.getText();
				String yearReleased = albumYearReleased.getText();
				int copies = Integer.parseInt( albumCopies.getText() );

				ArrayList<String> tags = parseTagline( tagline );
				ArrayList<String> songList = parseTagline( songline );

				//Validate input, add Album, save library, and close dialog
				if(  validateYear( yearReleased ) && copies >= 0 ) {
					lib.add( new Album( name, genre, id, copies, tags, artist, label, songList, Integer.parseInt( yearReleased )  )  );
					lib.save();
					JOptionPane.showMessageDialog( this, "Successfully added movie to library!", "Saved movie successfully", JOptionPane.INFORMATION_MESSAGE );
					dispose();
				}
				else
					JOptionPane.showMessageDialog( this, "Please ensure that your copies field and that your 4-digit year is valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
			}
			catch ( NumberFormatException nfe ) {
				JOptionPane.showMessageDialog( this, "Please check that the numbers you have entered are valid.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( nfe );
			} 
			catch ( NullPointerException npe ) {
				JOptionPane.showMessageDialog( this, "Please check that all fields are filled out correctly.", "Error! - Library Manager - Add Item", JOptionPane.ERROR_MESSAGE );
				System.err.println( npe );
			}
		}
	}

	/**
	 * Parses lists formatted with semicolon delimiters and puts the individual Strings into an ArrayList
	 * @param tagline The formatted line
	 * @return The ArrayList containing the Strings
	 */
	private final ArrayList<String> parseTagline( String tagline ) {
		ArrayList<String> tags = new ArrayList<String>();
		Scanner tagParser = new Scanner( tagline );
		tagParser.useDelimiter( ";" );
		while( tagParser.hasNext() ) {
			String s = tagParser.next().toLowerCase();
			if( ! tags.contains( s ) )
				tags.add( s );
		}
		tagParser.close();

		return tags;
	}

	/**
	 * Validates a year using the Java implementation of regular expressions.
	 * 
	 * <p>Validates 4-digit dates. Prefix dates not using 4 digits with zeroes. For example, the year 93 would become 0093.</p>
	 * @param yearToValidate The year to valdiate
	 * @return If the specified year matches the specified regular expression pattern.
	 */
	private final boolean validateYear( String yearToValidate ) {
		return Pattern.matches( "\\d{4}", yearToValidate );
	}

	/**
	 * Initializes the AddBook panel
	 */
	private final void initAddBookPanel() {
		//buttons
		this.submitBook = new JButton( "Add book" );
		this.submitBook.addActionListener( this );
		this.cancelBook = new JButton( "Cancel" );
		this.cancelBook.addActionListener( this );

		//Text fields
		this.bookName = new JTextField( 12 );
		this.bookGenre = new JTextField( 12 ); 
		this.bookAuthor = new JTextField( 12 );
		this.bookId = new JTextField( 12 );
		this.bookTags = new JTextField( 12 );
		this.bookCopies = new JTextField( 12 );
		this.bookPublisher = new JTextField( 12 );
		this.bookYearPublished = new JTextField( 12 );
		this.bookTags.setToolTipText( "Separate with semicolons" );

		//Adding components
		this.addBook.add( new JLabel( "Title:" ) );
		this.addBook.add( this.bookName );
		this.addBook.add( new JLabel( "Author: " ) );
		this.addBook.add( this.bookAuthor );
		this.addBook.add( new JLabel( "Genre: " ) );
		this.addBook.add( this.bookGenre );
		this.addBook.add( new JLabel( "Publisher: " ) );
		this.addBook.add( this.bookPublisher );
		this.addBook.add( new JLabel( "Year published: " ) );
		this.addBook.add( this.bookYearPublished );
		this.addBook.add( new JLabel( "Tags: " ) );
		this.addBook.add( this.bookTags );
		this.addBook.add( new JLabel( "Copies: " ) );
		this.addBook.add( this.bookCopies );
		this.addBook.add( new JLabel( "ISBN: " ) );
		this.addBook.add( this.bookId );
		this.addBook.add( this.submitBook );
		this.addBook.add( this.cancelBook );
	}

	/**
	 * Initializes the AddMovie panel
	 */
	private final void initAddMoviePanel() {
		//Buttons
		this.submitMovie = new JButton( "Add movie" );
		this.submitMovie.addActionListener( this );
		this.cancelMovie = new JButton( "Cancel" );
		this.cancelMovie.addActionListener( this );

		//Text fields
		this.movieName = new JTextField( 12 );
		this.movieGenre = new JTextField( 12 );
		this.movieId = new JTextField( 12 );
		this.movieTags = new JTextField( 12 );
		this.movieCopies = new JTextField( 12 );
		this.movieDirector = new JTextField( 12 ); 
		this.movieRating = new JTextField( 12 );
		this.movieYearReleased = new JTextField( 12 );
		this.movieStars = new JTextField( 12 );
		this.movieTags.setToolTipText( "Separate with semicolons" );
		this.movieStars.setToolTipText( "Separate with semicolons" );

		//Adding components
		this.addMovie.add( new JLabel( "Title: " ) );
		this.addMovie.add( this.movieName );
		this.addMovie.add( new JLabel( "Director: " ) );
		this.addMovie.add( this.movieDirector );
		this.addMovie.add( new JLabel( "Genre: " ) );
		this.addMovie.add( this.movieGenre );
		this.addMovie.add( new JLabel( "Starring: " ) );
		this.addMovie.add( this.movieStars );
		this.addMovie.add( new JLabel( "Rating: " ) );
		this.addMovie.add( this.movieRating );
		this.addMovie.add( new JLabel( "Tags: " ) );
		this.addMovie.add( this.movieTags );
		this.addMovie.add( new JLabel( "Year released: " ) );
		this.addMovie.add( this.movieYearReleased );
		this.addMovie.add( new JLabel( "Copies: " ) );
		this.addMovie.add( this.movieCopies );
		this.addMovie.add( new JLabel( "ID:" ) );
		this.addMovie.add( this.movieId );
		this.addMovie.add( this.submitMovie );
		this.addMovie.add( this.cancelMovie );
	}

	/**
	 * Initializes the AddAlbum panel
	 */
	private final void initAddAlbumPanel() {
		//Buttons
		this.submitAlbum = new JButton( "Add album" );
		this.submitAlbum.addActionListener( this );
		this.cancelAlbum = new JButton( "Cancel" );
		this.cancelAlbum.addActionListener( this );

		//Text fields
		this.albumName = new JTextField( 12 );
		this.albumGenre = new JTextField( 12 );
		this.albumId = new JTextField( 12 );
		this.albumTags = new JTextField( 12 );
		this.albumArtist = new JTextField( 12 );
		this.albumSongList = new JTextField( 12 );
		this.albumLabel= new JTextField( 12 );
		this.albumYearReleased= new JTextField( 12 );
		this.albumCopies = new JTextField( 12 );
		this.albumTags.setToolTipText( "Separate with semicolons" );
		this.albumSongList.setToolTipText( "Separate with semicolons" );

		//Adding components
		this.addAlbum.add( new JLabel( "Title: " ) );
		this.addAlbum.add( this.albumName );
		this.addAlbum.add( new JLabel( "Artist: " ) );
		this.addAlbum.add( this.albumArtist );
		this.addAlbum.add( new JLabel( "Genre: " ) );
		this.addAlbum.add( this.albumGenre );
		this.addAlbum.add( new JLabel( "Label: " ) );
		this.addAlbum.add( this.albumLabel );
		this.addAlbum.add( new JLabel( "Song list: " ) );
		this.addAlbum.add( this.albumSongList );
		this.addAlbum.add( new JLabel( "Tags: " ) );
		this.addAlbum.add( this.albumTags );
		this.addAlbum.add( new JLabel( "Year released: " ) );
		this.addAlbum.add( this.albumYearReleased );
		this.addAlbum.add( new JLabel( "Copies: " ) );
		this.addAlbum.add( this.albumCopies );
		this.addAlbum.add( new JLabel( "ID: " ) );
		this.addAlbum.add( this.albumId );
		this.addAlbum.add( this.submitAlbum );
		this.addAlbum.add( this.cancelAlbum );
	}

	private final void initAddVideoGamePanel() {		
		//Buttons
		this.submitVideoGame = new JButton( "Add video game" );
		this.submitVideoGame.addActionListener( this );
		this.cancelVideoGame = new JButton( "Cancel" );
		this.cancelVideoGame.addActionListener( this );

		//Text fields
		this.videoGameName = new JTextField( 12 );
		this.videoGameGenre = new JTextField( 12 );
		this.videoGameId = new JTextField( 12 );
		this.videoGameTags = new JTextField( 12 );
		this.videoGameConsole = new JTextField( 12 );
		this.videoGameDeveloper = new JTextField( 12 );
		this.videoGamePublisher = new JTextField( 12 );
		this.videoGameContentRating = new JTextField( 12 );
		this.videoGameCopies = new JTextField( 12 );
		this.videoGameTags.setToolTipText(" Separate with semicolons" );

		//Adding components
		this.addVideoGame.add( new JLabel( "Title: " ) );
		this.addVideoGame.add( this.videoGameName );
		this.addVideoGame.add( new JLabel( "Genre: " ) );
		this.addVideoGame.add( this.videoGameGenre );
		this.addVideoGame.add( new JLabel( "Console: " ) );
		this.addVideoGame.add( this.videoGameConsole );
		this.addVideoGame.add( new JLabel( "Developer: " ) );
		this.addVideoGame.add( this.videoGameDeveloper );
		this.addVideoGame.add( new JLabel( "Publisher " ) );
		this.addVideoGame.add( this.videoGamePublisher);
		this.addVideoGame.add( new JLabel( "Content rating: " ) );
		this.addVideoGame.add( this.videoGameContentRating );
		this.addVideoGame.add( new JLabel( "Tags: " ) );
		this.addVideoGame.add( this.videoGameTags );
		this.addVideoGame.add( new JLabel( "Id: " ) );
		this.addVideoGame.add( this.videoGameId );
		this.addVideoGame.add( new JLabel( "Copies: " ) );
		this.addVideoGame.add( this.videoGameCopies );
		this.addVideoGame.add( this.submitVideoGame );
		this.addVideoGame.add( this.cancelVideoGame );
	}

	private final void init( String itemType ) {
		//Set up panels
		this.addBook = new JPanel( new GridLayout( 0, 2 ) );
		this.addMovie = new JPanel( new GridLayout( 0, 2 ) );
		this.addVideoGame = new JPanel( new GridLayout( 0, 2 ) );
		this.addAlbum = new JPanel( new GridLayout( 0, 2 ) );

		initAddBookPanel();
		initAddMoviePanel();
		initAddAlbumPanel();
		initAddVideoGamePanel();

		//Setup card layout
		this.cardLayout = new CardLayout();
		this.cards = new JPanel( this.cardLayout );
		this.cards.add( this.addBook, this.BOOK );
		this.cards.add( this.addMovie , this.MOVIE);
		this.cards.add( this.addVideoGame, this.VIDEOGAME );
		this.cards.add( this.addAlbum, this.ALBUM );

		this.cardLayout.show( this.cards, itemType.toLowerCase() );

		super.add( this.cards );
	}
}