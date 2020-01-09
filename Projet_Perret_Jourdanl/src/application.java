import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Font;
import java.awt.Color;

public class application {

	private JFrame frame;
	private JTextField txtC;
	private JTextField txtBidulllmachiiiiin;
	private bdd bdd;
	private bdd bddfiltre;
	private JLabel lblOrdre;
	private JLabel lblDiamtre;
	private JLabel lblDegrMoyen;
	private JLabel lblVolume;
	private JLabel lblUtilisateursCentraux;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					application window = new application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBdd = new JLabel("Chemin : ");
		lblBdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(lblBdd, BorderLayout.WEST);
		
		txtC = new JTextField();
		txtC.setText("C:\\Users...");
		txtC.setToolTipText("C:\\Users...");
		panel_1.add(txtC, BorderLayout.CENTER);
		txtC.setColumns(10);
		
		JButton btnBdd = new JButton("Ok");
		btnBdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				bdd = new bdd(txtC.getText());
				affichage(bdd);
				btnBdd.setBackground(Color.green);
				if(bdd.getListeUser().size()<=250) {
					graph(bdd);
					lblDiamtre.setText("Diamètre : " + Integer.toString(bdd.diametre()));
				}
			}
		});
		panel_1.add(btnBdd, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFiltre = new JLabel("Filtre :     ");
		lblFiltre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_2.add(lblFiltre, BorderLayout.WEST);
		
		txtBidulllmachiiiiin = new JTextField();
		txtBidulllmachiiiiin.setText("bidulll,machiiiiin,...");
		panel_2.add(txtBidulllmachiiiiin, BorderLayout.CENTER);
		txtBidulllmachiiiiin.setColumns(10);
		
		JButton btnFiltre = new JButton("Ok");
		btnFiltre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String mots = txtBidulllmachiiiiin.getText();
				//On regarde d'abord le cas où le filtre contient plusieurs utilisateurs
					bddfiltre = new bdd();
					bddfiltre = bdd.filtrage(mots);
					if(bddfiltre.getBdd().size()!=0) {
						affichage(bddfiltre);
						
						if(bddfiltre.getListeUser().size()<=250) {
							graph(bddfiltre);
							lblDiamtre.setText("Diamètre : " + Integer.toString(bdd.diametre()));
						}
						btnFiltre.setBackground(Color.green);
					
					}
			}
				
					
		});
		
		panel_2.add(btnFiltre, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		Label titre = new Label("Statistiques principales :");
		titre.setForeground(Color.DARK_GRAY);
		titre.setFont(new Font("Tahoma", Font.BOLD, 20));
		titre.setAlignment(Label.CENTER);
		panel_3.add(titre, BorderLayout.NORTH);
		
		Panel panel_4 = new Panel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblVolume = new JLabel("Volume : - ");
		lblVolume.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblVolume);
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblOrdre = new JLabel("Ordre : - ");
		lblOrdre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblOrdre);
		lblOrdre.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblDiamtre = new JLabel("Diam\u00E8tre : - ");
		lblDiamtre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblDiamtre);
		lblDiamtre.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblDegrMoyen = new JLabel("Degr\u00E9 moyen : - ");
		lblDegrMoyen.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblDegrMoyen);
		lblDegrMoyen.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUtilisateursCentraux = new JLabel("Utilisateurs centraux : - ");
		lblUtilisateursCentraux.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblUtilisateursCentraux);
		lblUtilisateursCentraux.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	private void affichage(bdd bdd) {
		lblOrdre.setText("Ordre : " + Integer.toString(bdd.ordre()));
		lblDegrMoyen.setText("Degré moyen : " + Integer.toString(bdd.degrMoyen()));
		lblUtilisateursCentraux.setText("Utilisateurs centraux : "+ Arrays.toString(bdd.centralite()));
		lblVolume.setText("Volume : " + Integer.toString(bdd.volume()));
	}
	
	private void graph(bdd bdd) {
		JGraphXAdapterDemo applet = new JGraphXAdapterDemo();
        applet.init();
        applet.ajouterData(bdd);

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Graphique de la base de donnée");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}

}
