package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class FAQ extends Model {
	
	@Id
	public int id;
	
	@Required
	@MinLength(20)
	public String question;
	
	@Required
	@MinLength(20)
	public String answer;
	
	

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	static Finder<Integer, FAQ> find = new Finder<Integer, FAQ>(Integer.class, FAQ.class);
	
	public FAQ(String question, String answer){
		this.question = question;
		this.answer = answer;
	}
	
	public static int createFAQ(String question, String answer){
		FAQ newFaq = new FAQ(question, answer);
		newFaq.save();
		return newFaq.id;
	}
	
	public static List<FAQ> all(){
		List<FAQ> faqs = find.findList();
		return faqs;
	}
	
	public static boolean find(String question){
		return find.where().eq("question", question).findUnique() != null;
	}
	
	public static FAQ find(int id){
		return find.byId(id);
	}
	
	public static void delete(int id){
		find.byId(id).delete();
	}
	
	
	
	
}
