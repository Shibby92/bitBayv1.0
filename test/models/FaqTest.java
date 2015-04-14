package models;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import models.FAQ;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;
import static org.junit.Assert.*;

public class FaqTest extends WithApplication {
	
	@Before
	public void setUp() {
		fakeApplication(inMemoryDatabase());
	}

	/**
	 * Test creates one FAQ, checks if FAQ with that id exists, and checks if
	 * question and answer are correct.
	 */
	@Test
	public void testCreateFaq() {
		FAQ.createFAQ("question", "answer");
		FAQ f = FAQ.find(6);
		assertNotNull(f);
		assertEquals(f.question, "question");
		assertEquals(f.answer, "answer");
	}
	
	@Test
	public void testFindNonExistingFaq() {
		FAQ f = FAQ.find(1000);
		
		assertNull(f);
	}
	
	/**
	 * Test creates one FAQ, checks if FAQ with that id exists, and puts values
	 * using setters. After that, test checks if values are correct.
	 */
	@Test
	public void testEditFaq() {
		FAQ.createFAQ("question", "answer");
		FAQ f = FAQ.find(6);
		assertNotNull(f);
		f.setQuestion("Question2");
		f.setAnswer("Answer2");
		assertEquals(f.question, "Question2");
		assertEquals(f.answer, "Answer2");
	}
	
	/**
	 * Test creates one FAQ, checks if FAQ with that id exists, and deletes FAQ.
	 * After that, test checks if deleted FAQ is null.
	 */
	@Test
	public void testDeleteFaq() {
		FAQ.createFAQ("question", "answer");
		FAQ f = FAQ.find(6);
		assertNotNull(f);
		FAQ.delete(6);
		f = FAQ.find(6);
		assertNull(f);		
	}
}
