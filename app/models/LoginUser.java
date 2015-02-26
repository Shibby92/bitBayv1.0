package models;

import javax.persistence.Id;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

public class LoginUser extends Model {
	
	public String username;

	public String password;
	

}
