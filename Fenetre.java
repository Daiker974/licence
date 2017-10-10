package exercice_3_2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener{

	Panneau panel = new Panneau();	
	Animation anime = new Animation(this);
	boolean pause;
	Horloge temps = new Horloge();
	JLabel score = new JLabel("SCORE :  0 ",JLabel.CENTER);
	JButton bouton = new JButton("COMMENCER");
	JButton plus = new JButton(" + ");
	JButton moins = new JButton(" - ");
	int points;
	
	public Fenetre() {
		super("Application");
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container contenu = getContentPane();
		contenu.setLayout(new BorderLayout());
		contenu.add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.white);
		JPanel panel = new JPanel();
		contenu.add(panel,BorderLayout.SOUTH);
		panel.setBackground(new Color(223, 242, 255));
		bouton.addActionListener(this);
		plus.addActionListener(this);
		moins.addActionListener(this);
		bouton.setPreferredSize(new Dimension(120, 30)); 
		plus.setPreferredSize(new Dimension(50, 30)); 
		moins.setPreferredSize(new Dimension(50, 30));
		panel.add(bouton);
		panel.add(plus);
		panel.add(moins);
		JPanel panel_haut = new JPanel();
		contenu.add(panel_haut,BorderLayout.NORTH);
		panel_haut.setBackground(new Color(223, 242, 255));
		panel_haut.setLayout(new GridLayout(1,2));
		temps.setText("TEMPS : 0 s");
		temps.setHorizontalAlignment(JLabel.CENTER);
		panel_haut.add(temps);
		panel_haut.add(score);
		plus.setEnabled(false);
		moins.setEnabled(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Fenetre();
	}
	
	public void begin() {
		anime.start();
		temps.start();
		bouton.setText("STOP");
		panel.Balles[panel.nombre_balles] = new Balle(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)),
				(int) (Math.random() * (panel.getWidth()*0.75)),(int) (Math.random() * (panel.getWidth()*0.75)));
		panel.nombre_balles++;
		plus.setEnabled(true);
		moins.setEnabled(true);
	}
	
	public void swap() {
		if(bouton.getText().equals("START")) {
			bouton.setText("STOP");
			temps.start();
			pause = false;
		}
		else {
			pause = true;
			temps.stop();
			bouton.setText("START");
		}
	}
	
	public void ajout() {
		if(panel.nombre_balles<8) {
			Balle ball = new Balle(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)),
					(int) (Math.random() * (panel.getWidth()*0.75)),(int) (Math.random() * (panel.getWidth()*0.75)));
			panel.check(ball);
			panel.Balles[panel.nombre_balles] = ball;
			panel.nombre_balles++;
			moins.setEnabled(true);
		}
		if(panel.nombre_balles==8) {
			plus.setEnabled(false);
		}
	}
	
	public void supprimer() {
		if(panel.nombre_balles>0) {
			panel.nombre_balles--;
			panel.Balles[panel.nombre_balles] = null;
			if(!plus.isEnabled())plus.setEnabled(true);
		}
		if(panel.nombre_balles==0) {
			panel.Balles[panel.nombre_balles] = null;
			moins.setEnabled(false);
			plus.setEnabled(true);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		repaint();
		if(source == bouton) {
			if(bouton.getText().equals("COMMENCER")) begin();
			else swap();
		}
		if(source == plus) ajout();
		if(source == moins) supprimer();
	}
}