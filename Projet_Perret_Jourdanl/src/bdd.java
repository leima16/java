import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class bdd implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TreeSet<Tweet> bdd;
	
	public bdd() {
		bdd = new TreeSet<Tweet>();
	}
	
	public bdd(String txt) {
		bdd = new TreeSet<Tweet>();
		try{
			//Un fux est créer à partir de la base passer un paramètre
			InputStream flux=new FileInputStream(txt); 
			InputStreamReader lecture = new InputStreamReader(flux,"UTF-8");
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			//On boucle sur le fichier afin d'obtenir chaque ligne du fichier
			while ((ligne=buff.readLine())!=null){
				if(!ligne.equals("")) {//On traite le cas des données manquantes
					String[] tabligne = ligne.split("\\t",-1); // On stock dans un tableau les différents valeurs pour les variables du tweet
					String retweet;
					if (tabligne.length == 4) { //On traite le cas d'un tweet seul sans retweet
						Tweet t = new Tweet(tabligne[0],tabligne[1],tabligne[2],tabligne[3],null);
						//On ajoute à la base de données le tweet
						bdd.add(t);
						System.out.println(t.toString());
					}
					else if (tabligne.length == 5){ //Avec retweet
						retweet = tabligne[4]; 
						Tweet t = new Tweet(tabligne[0],tabligne[1],tabligne[2],tabligne[3],retweet);
						System.out.println(t.toString());
						//On ajoute à la base de données le tweet
						bdd.add(t);
					}
				} //Si le tweet n'a pas au minimum 4 variables (donnée aberrante) alors on ne prend pas en compte cette donnée
				
				
			}
			buff.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public TreeSet<Tweet> getBdd(){
		//Retourne la base de données (TreeSet)
		return bdd;
	}
	
	public void ajouter(Tweet t) {
		//Permet d'ajouter un tweet à la base de données
		bdd.add(t);
	}
	
	public TreeSet<String> getListeUser(){
		//Retourne la liste de tous les users de la base de données
		TreeSet<String> liste = new TreeSet<String>();
		Iterator<Tweet> iter=bdd.iterator();
		//On boucle sur la bdd
		while(iter.hasNext()) {
			Tweet t = iter.next(); // On récupère un tweet
			String uti1 = t.getUserID() ;
			String uti2 = t.getRetweet();
			if (!liste.contains(uti1) && uti1.length()!=0) { 
				//si le pseudo du user n'existe pas encore dans la liste des users alors on le rejoute
				 liste.add(uti1);
			}
			if (!liste.contains(uti2) && uti2.length()!=0) {
				//si le pseudo du user retweeter n'existe pas encore dans la liste des users alors on le rejoute
				 liste.add(uti2);
			}
		}		
		return liste;
	}
	
	public TreeSet<String> getListAdjacent(String noeud){
		//Retourne une liste des sommets adjacent au noeud passé en paramètre
		TreeSet<String> liste = new TreeSet<String>();
		Iterator<Tweet> iter=bdd.iterator();
		//On boucle sur la base de données
		while(iter.hasNext()) {
			Tweet t = iter.next(); // On récupère le tweet
			String uti = t.getUserID();
			String ret = t.getRetweet();
			if(uti.equals(noeud) && !liste.contains(ret) && ret.length()!=0) {
				//Si le pseudo du user est égal au noeud du paramètre et que le user retweeter n'existe pas encore dans la liste alors on le rejoute
				liste.add(ret);
			}
			if(ret.equals(noeud) && !liste.contains(uti) && uti.length()!=0) {
				//Si le pseudo du user retweeter est égal au noeud du paramètre et que le user n'existe pas encore dans la liste alors on le rejoute
				liste.add(uti);
			}
		}
		return liste;
		
	}
	
	public TreeSet<String> getListRt(String noeud){
		//Permet d'avoir tous les retweet du user
		TreeSet<String> liste = new TreeSet<String>();
		Iterator<Tweet> iter=bdd.iterator();
		//On boucle sur la bdd
		while(iter.hasNext()) {
			Tweet t = iter.next();
			String uti = t.getUserID();
			String ret = t.getRetweet();
			//On regarde si le nom de l'utilisateur est le même que celui demandé et on regarde si le nom de l'utilisateur retweeter
			//n'est pas déjà dans la liste
			if(uti.equals(noeud) && !liste.contains(ret) && ret.length()!=0) {
				liste.add(ret);
			}
		}
		return liste;
		
	}
	
	public bdd filtrage(String mots) {
		//Cette fonction va permettre de créer une nouvelle bdd avec que quelques tweets préciser par l'utilisateur précédement
		
		bdd newbdd = new bdd();
		TreeSet<String> listeuse = getListeUser();
		
		//On regarde d'abord le cas où le filtre contient plusieurs users saisies
		if(mots.contains(",") && mots.length()>0) {
			String[] users = mots.split(",",-1);//On récupère dans un tableaux les différents users demandés puis on boucle sur eux
			//On contrôle d'abord que les users saisies sont bien dans la base de données
			for (int j=0; j<users.length;j++) {
				if(!listeuse.contains(users[j])) {
					System.out.println(users[j] + " n'existe pas dans la base de données");
					return newbdd;
				}
			}
			
			//On va chercher dans la base de données tous les tweets faisant références aux users filtrés.
			for (int i=0; i<users.length;i++) {
				Iterator<Tweet> iter=bdd.iterator();
				//On boucle sur la bdd
				while(iter.hasNext()) {
					Tweet t = iter.next();
					String uti = t.getUserID();
					String ret = t.getRetweet();
					//On compare les utilisateurs (tweet et retweet) de la bdd avec l'utilisateur du filtre
					if(uti.equals(users[i]) || ret.equals(users[i]) && ret.length()!=0) {
						//On ajoute le tweet à la nouvelle bdd
						//System.out.println(uti.equals(users[i]) || ret.equals(users[i]));
						newbdd.ajouter(t);
					}
				}
			}
		}
		
		
		//On regarde le cas où l'utilisateur n'a précisé aucun filtrage
		else if (mots.length() == 0) {
			System.out.println("Vous n'avez pas saisi de filtre");
			return newbdd;
		}

		
		//On regarde le cas où il n'y a qu'un user dans le filtre
		else {
			//On regarde que le user saisi soit bien dans la base de donnée
			if(!listeuse.contains(mots)) {
				System.out.println(mots + " n'existe pas dans la base de données");
				return newbdd;
			}
			//On cherche les tweets réalisé par le user saisi ou les tweets de celui-ci retweeter
			Iterator<Tweet> iter=bdd.iterator();
			while(iter.hasNext()) {
				Tweet t = iter.next();
				String uti = t.getUserID();
				String ret = t.getRetweet();
				//On compare les utilisateurs (tweet et retweet) de la bdd avec le user du filtre
				if(uti.equals(mots) || ret.equals(mots) && ret.length()!=0) {
					newbdd.ajouter(t);
				}
			}
		}
		return newbdd;
		
	}
	
	public int degrMoyen() {
		//Retourne le degré moyen du graph
		int sum = 0;
		TreeSet<String> liste = getListeUser();
		Iterator<String> iter = liste.iterator();
		//On boucle sur la liste total des users
		while(iter.hasNext()) {
			String ligne = iter.next();
			TreeSet<String> listeadja = getListAdjacent(ligne);// On regarde les sommets adjacent au tweet
			sum+=listeadja.size(); //On ajoute à la somme le nombre de sommets adjacents au tweet
		}
		return (sum/liste.size()); // On divise la somme par la taille de la liste des users total.
		
	}
	
	public int ordre() {
		//Retourne l'ordre du graph
		TreeSet<String> liste = getListeUser();
		return liste.size();
	}
	
	public int[][] matriceadj(){
		//Cet agorithme permet de créer une matrice adjacente à partir des données
		TreeSet<String> listeuser = getListeUser();
		int dim = listeuser.size();
		//System.out.println(listeuser);
		int[][] matrice= new int[dim][dim];
		int comptuse = 0;
		Iterator<String> iter = listeuser.iterator();
		//On itère d'abord sur les utilisateurs
		while(iter.hasNext()) {
			String ligne = iter.next();
			TreeSet<String> listeadj = getListAdjacent(ligne);
			Iterator<String> iter2 = listeadj.iterator();
			//On itère sur les sommets adjacents à l'utilisateur choisis précèdemment
			while(iter2.hasNext()) {
				String ligneadj = iter2.next();
				//System.out.println(ligneadj);
				Iterator<String> iter3 = listeuser.iterator();
				//On itère une fois de plus sur les utilsateurs pour trouver la place du sommet adjacent
				int comptadj = 0;
				while(iter3.hasNext()) {
					String ligneus = iter3.next();
					if(ligneus.equals(ligneadj)) {
						matrice[comptuse][comptadj] =1;
					}
					//System.out.println(comptadj);
					comptadj+=1;
				}
			}
			comptuse +=1;
		}
		
		return matrice;
		
	}
	
	public int minDistance(int dist[], Boolean sptSet[], int V)
    {
		//https://riptutorial.com/fr/algorithm/example/23947/algorithme-du-plus-court-chemin-de-dijkstra
        int min = Integer.MAX_VALUE, min_index=-1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }
	
	public int dijkstra(int graph[][], int src, int V)
    {
		//Retourne le maximum des plus courts chemin du user src
		// V : taille du graph
		//graph[][] : matrice adjacente du graph
//https://riptutorial.com/fr/algorithm/example/23947/algorithme-du-plus-court-chemin-de-dijkstra
        Boolean sptSet[] = new Boolean[V];
        int dist[] = new int[V];
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        dist[src] = 0;

        for (int count = 0; count < V-1; count++)
        {
            int u = minDistance(dist, sptSet,V);

            sptSet[u] = true;

            for (int v = 0; v < V; v++)

                if (!sptSet[v] && graph[u][v]!=0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u]+graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }
        //Code réalisé par nous
        int max = 0;
        for(int j=0; j<dist.length;j++) {
        	if(dist[j] != Integer.MAX_VALUE && max<dist[j]) {
        		//On ne prend pas en compte les distances égales à l'infini car le graph aura obligatoirement des user qui auront tweeter une fois sans retweeter une autre personne
        		max = dist[j];
        	}
        }
        return max;
    }
	
	public int diametre() {
		//Retourne le diamètre du graph
		int[][] mat = matriceadj();
		TreeSet<String> liste = getListeUser();
		
		int max = 0;
		System.out.println(liste.size());
		int compt = 0;
		//On boucle sur la liste des users
		for(int i=0; i<liste.size();i++){
			//On effectu le le calcul du maximum du plus court chemin pour chaque user
			int chemin =dijkstra(mat,compt,liste.size());
			if(chemin>max) { // Si le max est plus grand que l'ancien alors on remplace l'ancien par le nouveau
				max=chemin;
			}
			compt+=1;
		}
		return max;
		
	}
	
	public int volume() {
		//Retourne le volume du graph
		Iterator<Tweet> iter=bdd.iterator();
		int sum =0;
		//On boucle sur la bdd
		while(iter.hasNext()) {
			Tweet t = iter.next();
			if(t.getRetweet().length()!=0) {
				sum+=1;
			}
		}
		return sum;
	}
	
	
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		//Permet de trier une maps à partir de ses valeurs des plus grandes aux plus petites
		//https://beginnersbook.com/2014/07/how-to-sort-a-treemap-by-value-in-java/
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0) 
					return 1;
				else 
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
	
	
	public String[] centralite() {
		//Retourne la liste des users centraux
		TreeMap<String,Integer> listecentre= new TreeMap<String,Integer>();
		Iterator<Tweet> iter=bdd.iterator();
		//On itère sur la bdd
		while(iter.hasNext()) {
			Tweet t = iter.next();
			String uti = t.getUserID();
			String ret = t.getRetweet();
			//On regarde si la liste des users centraux contient le user si non on l'insère et si oui alors on ajoute 1 
			if(listecentre.containsKey(uti)) {
				listecentre.put(uti, listecentre.get(uti)+1);
			}
			else {
				listecentre.put(uti, 1);
			}
			//On regarde si la liste des users centraux contient le user retweeter si non on l'insère et si oui alors on ajoute 1 
			if(listecentre.containsKey(ret) ) {
				listecentre.put(ret, listecentre.get(ret)+1);
			}
			else if(ret.length()!=0){
				listecentre.put(ret, 1);
			}
		}
		//On trie la liste des users centraux de plus grand au plus petit
		TreeMap<String,Integer> listesort = (TreeMap<String, Integer>) sortByValues(listecentre);
		String[] listeusercentre = new String[5];
		
		//On récupère qu eles 5 users principaux
		Set set = listesort.entrySet();
		Iterator itr = set.iterator();
		int compt = 0;
		while(itr.hasNext() && compt<5) {
			Map.Entry mentry = (Map.Entry)itr.next();
			listeusercentre[compt] = mentry.getKey().toString();
	       
	        compt+=1;
		}
		return listeusercentre ;
	}
	
	
	
	public String toString() {
		//Cette fonction permet de transformer un tweet en une chaine de caractère
		String l ="";
		int i = 1;
		Iterator<Tweet> iter=bdd.iterator();
		while(iter.hasNext()) {
			Tweet t = iter.next();
			l += "--Tweet " + i +"--\n";
			l +=t.toString();
			i++;
		}
		return l;
	}
	public int size() {
		return bdd.size();
	}
	
	public static void main(String[] args){
		bdd j = new bdd("C:/Users/Leïla/Documents/M1 Informatique/JavaData/Foot3.txt");
		bdd fil = j.filtrage("LayeKamara,nico_psssg");
		System.out.println(Integer.toString(fil.dijkstra(fil.matriceadj(), 1, fil.getListeUser().size())));
    }

}
