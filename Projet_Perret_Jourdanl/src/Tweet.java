import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tweet implements Comparable<Object>{
	
	private String id;
	private String userID;
	private Date date;
	private String texte;
	private String retweet;
	
	public Tweet(String id,String userID,String date,String texte,String retweet) {
		this.id = id;
		this.userID = userID;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(date.contains(".")) { //on regarde si la date contient des milisencondes
				int point = date.indexOf(".");
	            Date datef = formatter.parse(date.substring(0, point)); //On enlève les milisecondes de la date
	            this.date = datef;
			}
			else {
	            Date datef = formatter.parse(date);
	            this.date = datef;
			}
			
        } catch (ParseException e) {
            e.printStackTrace();
        }
		this.texte = texte;
		this.retweet = retweet;
	}
	
	//Les premières méthodes implémentées ont été les getteurs des attributs de la classe
	public String getId() {
		return id;
	}
	
	public String getUserID() {
		return userID;
	}
	
	
	public Date getDate() {
		return date;
	}
	
	public String getTexte() {
		return texte;
	}
	
	public String getRetweet() {
		return retweet;
	}
	
	@Override
	public int compareTo(Object o) {
		// La méthode va permettre de comparer la chaîne de l'objet o avec le texte du tweet
		int resultat;
		if (this.texte.length() < o.toString().length()) {
			resultat = o.toString().length();
		}
		else if (this.texte.length() > o.toString().length()) {
			resultat = -(this.texte.length());
		}
		else {
			resultat = 0;
		}
		return resultat;
	}
	
	public String toString() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		String datestring = df.format(date);
        String st =  "ID : " + getId() + "\n";
        st +=  "UserID : " + getUserID() + "\n";
        st +=  "Date : " + datestring + "\n";
        st +=  "Texte : " + getTexte() + "\n";
        st +=  "Retweet : " + getRetweet() + "\n";
        return st;
	}

	
	
}
