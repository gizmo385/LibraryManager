package com.gizmo385.lm.tests;

import java.util.ArrayList;

import com.gizmo385.lm.ItemLibrary;
import com.gizmo385.lm.types.Book;
import com.gizmo385.lm.types.VideoGame;

public class TestObjectLoading {
	public static void main( String[] args ) {
		ItemLibrary il = new ItemLibrary();
		
		il.truncateLibrary();
		il.add( new Book( "TestBook", "TestGenre", "TestID", 5, new ArrayList<String>(), "TestAuthor", "TestPublisher", 1994) );
		il.add( new VideoGame( "TestVideoGame", "TestVideoGameGenre", "TestVideoGameID", 7, new ArrayList<String>(), "TestDeveloper", "TestPublisher", "TestConsole", "TestContentRating" ) );
		
		System.out.println( "Save status: " + il.save() );
		
		il = new ItemLibrary();
		
		System.out.println( "Load status: "  + il.load() );
	}
}
