import java.awt.*;
import java.awt.geom.AffineTransform;
import java.applet.Applet;

public class MyAvatar extends Applet{
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		// set canvas size to 700x700 with white background
		this.setSize(700,700);
		AffineTransform old = g2d.getTransform();
		
		Color skinColor = new Color(255,226,220);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,700,700);
		Dimension d = this.getSize();
	
		// name in the bottom-right corner
		g2d.setColor(Color.BLACK);
		g2d.setFont(g2d.getFont().deriveFont(30f));
		g2d.drawString("Putti",d.width-120,d.height-10);
			
		// face shape
		g2d.setColor(skinColor);
		g2d.fillOval(200, 110, 310, 500);
		
		// right ear
		g2d.setColor(skinColor);
		Polygon polygonRightEar = new Polygon();
		polygonRightEar.addPoint(510, 337);
		polygonRightEar.addPoint(539, 356);
		polygonRightEar.addPoint(528, 385);
		polygonRightEar.addPoint(524, 419); 
		polygonRightEar.addPoint(498, 445);  
		g2d.fillPolygon(polygonRightEar);
		g2d.fillArc(498,337,40,40,-20,170);
		g2d.fillArc(476, 405, 50, 40, 200, 200);
		
		// left ear
		Polygon polygonLeftEar = new Polygon();
		polygonLeftEar.addPoint(200, 337);
		polygonLeftEar.addPoint(170, 356);
		polygonLeftEar.addPoint(180, 385);
		polygonLeftEar.addPoint(182, 419); 
		polygonLeftEar.addPoint(215, 445);  
		g2d.fillPolygon(polygonLeftEar);
		g2d.setColor(skinColor);
		g2d.fillArc(170, 330, 40, 40, 30, 170);
		g2d.fillArc(180, 400, 50, 40, 170, 200);
	
		// face outline
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(new Color(0f,0f,0f,0.05f));
		g2d.drawOval(200, 110, 310, 500);
		g2d.setStroke(new BasicStroke(1));
		
		// hair
		g2d.setColor(Color.BLACK);
		
		int[] xPointsHair = {192,182,180,200,220,250,280,320,370,410,
							 460,480,495,520,530,535,510,500,490,470,
							 450,460,400,450,360,410,310,300,260,230,215};
		
		int[] yPointsHair = {407,287,232,150,130,105,100,90,85,97,
							 105,120,140,180,220,275,410,289,255,220,
							 190,230,180,230,170,220,165,170,190,230,270};
		
		int nPointsHair = xPointsHair.length;
		
		for(int i = 1; i < nPointsHair; i++){
			yPointsHair[i] += 25;
		}
		g2d.fillPolygon(xPointsHair,yPointsHair,nPointsHair);
		
		// Hair shadow
		g2d.setColor(new Color(0f,0f,0f,0.05f));
		g2d.setStroke(new BasicStroke(7));
		
		for(int i = 0; i < nPointsHair; i++){
			yPointsHair[i] += 4;
		}
		yPointsHair[0] -= 16;
		yPointsHair[16] -= 20;
		
		g2d.drawPolygon(xPointsHair,yPointsHair,nPointsHair);
		
		// left pupil
		g2d.setColor(new Color(0f,0f,0f,0.3f));
		g2d.fillOval(249,337,70,27);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(257,337,60,24);
		g2d.setColor(new Color(113,52,23)); //light brown
		g2d.fillOval(d.width/2-77,d.height/2-15,33,28);
		g2d.setColor(new Color(81,33,17)); //dark brown
		g2d.fillOval(d.width/2-70,d.height/2-12,20,20);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(d.width/2-65,d.height/2-7,10,10);
		
		// eyes
		
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.BLACK);
		g2d.drawArc(250,337,70,24,0,180);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawArc(250,332,70,30,170,190);

		// right pupil

		g2d.setColor(new Color(0f,0f,0f,0.3f));
		g2d.fillOval(249+147,337,70,27);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(250+150,337,60,25);
		
		
		g2d.setColor(new Color(113,52,23)); //light brown
		g2d.fillOval(d.width/2-77+140,d.height/2-15,33,28);
		g2d.setColor(new Color(81,33,17)); //dark brown
		g2d.fillOval(d.width/2-70+140,d.height/2-12,20,20);
		
		g2d.setColor(Color.BLACK);
		g2d.fillOval(d.width/2-65+140,d.height/2-7,10,10);
				
		// right eye
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.BLACK);
		g2d.drawArc(250+145,337,70,24,0,180);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawArc(250+145,332,70,30,170,190);
		

		// eyebrows
		g2d.setColor(Color.BLACK);
		
		int[] xPointsRightBrow = {460,390,380,460,485};			
		int[] yPointsRightBrow = {290,310,320,310,315};
		g2d.fillPolygon(xPointsRightBrow,yPointsRightBrow,5);
		
		// right-eyebrow shadow
		g2d.setColor(new Color(0f,0f,0f,0.3f));
		
		for(int i = 0; i < 5; i++){
			yPointsRightBrow[i] += 1;
		}
		g2d.setStroke(new BasicStroke(3));
		g2d.drawPolygon(xPointsRightBrow,yPointsRightBrow,5);
		

		g2d.setColor(Color.BLACK);		
		int[] xPointsLeftBrow = {250,225,222,245,320,330,300};			
		int[] yPointsLeftBrow = {287,305,315,307,317,312,296};
		g2d.fillPolygon(xPointsLeftBrow,yPointsLeftBrow,7);
		
		// left-eyebrow shadow
		g2d.setColor(new Color(0f,0f,0f,0.3f));
		
		for(int i = 0; i < 5; i++){
			xPointsLeftBrow[i] += 1;
			yPointsLeftBrow[i] += 1;
		}
		xPointsLeftBrow[4] -= 7;
		xPointsLeftBrow[5] -= 9;
		xPointsLeftBrow[6] -= 9;
		yPointsLeftBrow[4] -= 2;
		yPointsLeftBrow[5] -= 2;
		yPointsLeftBrow[6] -= 2;
		g2d.setStroke(new BasicStroke(4));
		g2d.drawPolygon(xPointsLeftBrow,yPointsLeftBrow,5);
		
		// eyeglasses 
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawRoundRect(215,d.height/2-20,125,68,20,40);
		
		g2d.drawRoundRect(215+158,d.height/2-20,125,68,20,30);		
		g2d.setStroke(new BasicStroke(5));

			// glasses frame-joint
			g2d.drawArc(d.width/2-10,d.height/2-10,32,10,0,180);
			
			// left glasses leg
			int[] xPointsLeftLeg = {215,200,200,215};			
			int[] yPointsLeftLeg = {340,350,375,370};
			g2d.fillPolygon(xPointsLeftLeg,yPointsLeftLeg,4);
			
			// right glasses leg
			int[] xPointsRightLeg = {497,510,510,497};			
			int[] yPointsRightLeg = {340,345,370,365};
			g2d.fillPolygon(xPointsRightLeg,yPointsRightLeg,4);
			
			// glasses frame bold
			g2d.drawArc(d.width/2-132,d.height/2-20,120,18,0,180);
			g2d.drawArc(d.width/2-132+158,d.height/2-20,120,18,0,180);
			
			g2d.drawLine(d.width/2-132,d.height/2-10,d.width/2-132,d.height/2+10);
			g2d.drawLine(d.width/2-132+156,d.height/2-10,d.width/2-132+156,d.height/2+10);
			
			g2d.drawLine(d.width/2-132+123,d.height/2-10,d.width/2-132+123,d.height/2+10);
			g2d.drawLine(d.width/2-132+121+156,d.height/2-10,d.width/2-132+121+156,d.height/2+10);
			
	
		// nose
		g2d.setStroke(new BasicStroke(1));
		g2d.drawArc(d.width/2-60,d.height/2-40,50,120,285,70);
		g2d.drawArc(d.width/2+23,d.height/2-40,50,120,190,60);
			
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(new Color(0f,0f,0f,0.4f));
		g2d.drawArc(d.width/2-60,d.height/2-40,50,120,285,70);
		g2d.drawArc(d.width/2+23,d.height/2-40,50,120,190,60);
		
		// big fat nose XD
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLACK);
		g2d.setColor(new Color(0f,0f,0f,0.5f));
		g2d.drawArc(d.width/2-35, d.height/2+70, 25, 40, 90, 190);
		g2d.drawArc(d.width/2+22, d.height/2+70, 25, 40, 260, 180);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(new Color(0f,0f,0f,0.3f));
		g2d.drawArc(d.width/2-5, d.height/2+107, 20, 5, 180, 180);
		
		// nostrils
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillOval(d.width/2-20,d.height/2+100,15,10);
		g2d.fillOval(d.width/2+15,d.height/2+100,15,10);
	
		// lips
		// upper upper lips
		g2d.setColor(new Color(255,207,207));
		g2d.rotate(Math.toRadians(15));
		g2d.fillArc(d.width/2+125,d.height/2+47,70,30,45,135);
		g2d.rotate(Math.toRadians(-30));
		g2d.fillArc(d.width/2-202,d.height/2+230, 60, 25, -10, 170);
		g2d.setTransform(old);
		
		// lower upper lips
		g2d.fillArc(d.width/2-50,d.height/2+170,110,10,180,180);
		
		// upper lower lips
		g2d.setColor(new Color(255,207,207));
		g2d.fillArc(d.width/2-50,d.height/2+175,110,10,180,180);
		g2d.fillArc(d.width/2-48,d.height/2+158,105,45,180,180);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawArc(d.width/2-48,d.height/2+155,105,45,180,180);
	
		// upper lips outline
		g2d.setColor(Color.DARK_GRAY);
		g2d.rotate(Math.toRadians(15));
		g2d.drawArc(d.width/2+125,d.height/2+47,70,30,45,135);
		g2d.rotate(Math.toRadians(-30));
		g2d.drawArc(d.width/2-202,d.height/2+230, 60, 25, -10, 170);
		g2d.setTransform(old);
		
		g2d.setStroke(new BasicStroke(2));
		g2d.drawArc(d.width/2-50,d.height/2+170,110,10,180,180);
	}
	
}