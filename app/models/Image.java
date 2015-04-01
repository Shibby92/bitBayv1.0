package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Image extends Model {
	
	public List<String> image_url;
	public int product_id;

}
