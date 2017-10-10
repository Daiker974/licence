package exercice_3_2;

public class Animation extends Thread{
	
	Fenetre fen;
	
	public Animation(Fenetre fen){
		this.setDaemon(true);
		this.fen = fen;
	}
	
	public void run() {
		try {
			while(true) {
				if(!fen.pause) {
					fen.panel.move();
					if(fen.panel.collision()) {
						if(fen.panel.nombre_balles>0) {
							if(!fen.plus.isEnabled()) fen.plus.setEnabled(true);
						}
						if(fen.panel.nombre_balles==0) {
							fen.moins.setEnabled(false);
							fen.plus.setEnabled(true);
						}
						if(fen.panel.nombre_balles==8) {
							fen.plus.setEnabled(false);
						}
						fen.points++;
						fen.score.setText("SCORE :   " + fen.points);
					}
					sleep(10);
				}
				else sleep(10);
			}
		} catch (InterruptedException e) {}
	}
}





