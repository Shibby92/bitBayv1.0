package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class FAQ.
 */
@Entity
public class FAQ extends Model {
	
	/** The id. */
	@Id
	public int id;
	
	/** The question. */
	@Required
	@MinLength(20)
	public String question;
	
	/** The answer. */
	@Required
	@MinLength(20)
	@Column(columnDefinition = "TEXT")
	public String answer;

	/** The find. */
	static Finder<Integer, FAQ> find = new Finder<Integer, FAQ>(Integer.class, FAQ.class);
	
	/**
	 * Instantiates a new faq.
	 *
	 * @param question String the question
	 * @param answer String the answer
	 */
	public FAQ(String question, String answer){
		this.question = question;
		this.answer = answer;
	}
	
	/**
	 * Creates the faq.
	 *
	 * @param question String the question
	 * @param answer String the answer
	 * @return the id of the faq
	 */
	public static int createFAQ(String question, String answer){
		FAQ newFaq = new FAQ(question, answer);
		newFaq.save();
		return newFaq.id;
	}
	
	/**
	 * Finds all FAQs in database.
	 *
	 * @return the list
	 */
	public static List<FAQ> all(){
		List<FAQ> faqs = find.findList();
		return faqs;
	}
	
	/**
	 * Finds FAQ by its question.
	 *
	 * @param question String the question
	 * @return true, if successful
	 */
	public static boolean find(String question){
		return find.where().eq("question", question).findUnique() != null;
	}
	
	/**
	 * Find FAQ by its id.
	 *
	 * @param id int the id
	 * @return the faq
	 */
	public static FAQ find(int id){
		return find.byId(id);
	}
	
	/**
	 * Deletes the FAQ by its id.
	 *
	 * @param id int the id
	 */
	public static void delete(int id){
		find.byId(id).delete();
	}
	
	
	
	
}
