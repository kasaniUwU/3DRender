import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static BufferedImage zBuffer=new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
    public static BufferedImage blackness=new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
    public static double[][] zBufferDist =new double[800][600];
    public static Vector3 move=new Vector3(0,0,0);
    static Camera player= new Camera();
    static double sign(Vector3 p1, Vector3 p2, Vector3 p3)
    {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    static boolean PointInTriangle(Vector3 pt, Vector3 v1, Vector3 v2, Vector3 v3)
    {
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(pt, v1, v2);
        d2 = sign(pt, v2, v3);
        d3 = sign(pt, v3, v1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }
    public static void main(String[] args) {

        player.position=new Vector3(6,0,0);
        player.rotation=new Vector3(0,-3.14/2.0,0);
        player.scale=new Vector3(800,600,0);
        JFrame mainF=new JFrame();

        Mesh cube=new Mesh("src/cube.obj");
        Mesh cube2=new Mesh("src/cube.obj");

        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D G2d=(Graphics2D) g;
                //G2d.drawRenderedImage(zBuffer,null);
                Graphics g2=zBuffer.getGraphics();
                g2.setColor(Color.black);
                g2.fillRect(0,0,800,600);
                List<List<int[]>> a=player.renderMesh(cube);
                List<List<Vector3>> aB=player.renderMeshPoints(cube);
                for(int i=0; i<a.size(); i++) {
                    int X = a.get(i).get(0)[0];
                    if (X < a.get(i).get(0)[1]) {
                        X = a.get(i).get(0)[1];
                    }
                    if (X < a.get(i).get(0)[2]) {
                        X = a.get(i).get(0)[2];
                    }
                    int Y = a.get(i).get(1)[0];
                    if (Y < a.get(i).get(1)[1]) {
                        Y = a.get(i).get(1)[1];
                    }
                    if (Y < a.get(i).get(1)[2]) {
                        Y = a.get(i).get(1)[2];
                    }
                    int X2 = a.get(i).get(0)[0];
                    if (X2 > a.get(i).get(0)[1]) {
                        X2 = a.get(i).get(0)[1];
                    }
                    if (X2 > a.get(i).get(0)[2]) {
                        X2 = a.get(i).get(0)[2];
                    }
                    int Y2 = a.get(i).get(1)[0];
                    if (Y2 > a.get(i).get(1)[1]) {
                        Y2 = a.get(i).get(1)[1];
                    }
                    if (Y2 > a.get(i).get(1)[2]) {
                        Y2 = a.get(i).get(1)[2];
                    }
                    if (!(X >= 800 || X2 <= 0||Y >= 600 || Y2 <= 0)) {
                        Vector3 P1 = new Vector3(aB.get(i).get(0).x, aB.get(i).get(0).y, aB.get(i).get(0).z);
                        Vector3 P2 = new Vector3(aB.get(i).get(1).x, aB.get(i).get(1).y, aB.get(i).get(1).z);
                        Vector3 P3 = new Vector3(aB.get(i).get(2).x, aB.get(i).get(2).y, aB.get(i).get(2).z);
                        for (int x = X2; x < X; x++) {
                            for (int y = Y2; y < Y; y++) {
                                Vector3 p1 = new Vector3(a.get(i).get(0)[0], a.get(i).get(1)[0], 0);
                                Vector3 p2 = new Vector3(a.get(i).get(0)[1], a.get(i).get(1)[1], 0);
                                Vector3 p3 = new Vector3(a.get(i).get(0)[2], a.get(i).get(1)[2], 0);
                                if (PointInTriangle(new Vector3(x, y, 0), p1, p2, p3)) {
                                    Vector3 p = new Vector3(x, y, 0);

                                    double w1 = ((p2.y - p3.y) * (p.x - p3.x) + (p3.x - p2.x) * (p.y - p3.y)) / ((p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y));
                                    double w2 = ((p3.y - p1.y) * (p.x - p3.x) + (p1.x - p3.x) * (p.y - p3.y)) / ((p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y));
                                    double w3 = 1 - w1 - w2;
                                    Color c1 = new Color((int) (w1 * 255.0), (int) (w2 * 255.0), (int) (w3 * 255.0));
                                    //add the vectors
                                    List<Integer> o=cube2.faces.get(i);
                                    Vector3 PO1 =cube2.points.get(o.get(0)-1);
                                    Vector3 PO2 =cube2.points.get(o.get(1)-1);
                                    Vector3 PO3 =cube2.points.get(o.get(2)-1);
                                    //make dist camra based
                                    Vector3 d1 = Vector3.multiply(PO1, w1);
                                    Vector3 d2 = Vector3.multiply(PO2, w2);
                                    Vector3 d3 = Vector3.multiply(PO3, w3);
                                    Vector3 df = Vector3.add(Vector3.add(d1, d2), d3);
                                    if (zBufferDist[x][y] == 0 || zBufferDist[x][y] > df.getLength()) {
                                        zBufferDist[x][y] = Math.abs(Vector3.subtract(player.position,df).getLength());

                                        FragShader shadeInfo = new FragShader(df, P1, P2, P3,PO1,PO2,PO3 );
                                        //g.setColor(Diffuse.getPoint(shadeInfo));
                                        //g.drawLine(x, y, x + 1, y + 1);
                                        Color c=Diffuse.getPoint(shadeInfo);
                                        zBuffer.setRGB(x,y,c.getRGB());
                                    }
                                }
                            }
                        }
                        //img.setRGB(x, y, Color.RED.getRGB());
                    }
                }

                /*
                draws points on the canvas
                 */
                /*
                for(int i=0; i<a.size(); i++) {
                    Polygon poly = new Polygon(a.get(i).get(0), a.get(i).get(1), a.get(i).get(0).length);
                    Color t=new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255));
                    zBuffer.getGraphics().setColor(t);
                    zBuffer.getGraphics().drawPolygon(poly);
                }

                 */


                //System.out.println("time:"+System.currentTimeMillis());
                //System.out.println("time:"+System.currentTimeMillis()/1000000);
                //g.setColor(Color.black);
                //g.fillRect(0,0,800,600);
                g.drawImage(zBuffer,0,0,null);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            synchronized (Main.class) {
                if (event.getID() == KeyEvent.KEY_PRESSED) move.x=1;
                else if (event.getID() == KeyEvent.KEY_RELEASED) move.x=0;
                return false;
            }
        });

            //makes render visable
        mainF.getContentPane().setBackground( new Color(0,0,0) );
        mainF.getContentPane().add(p);
        mainF.setSize(800, 600);
        mainF.setVisible(true);
        mainF.createBufferStrategy(4);
        //mainF.repaint();
        TimerTask  timer =new TimerTask() {
            @Override
            public void run() {
                mainF.repaint();
                player.position.x+=(int)move.x;
                //mainF.repaint();
                //mainF.update(mainF.getGraphics());
                //mainF.render();
            }
        };
        Timer e=new Timer();
        //e.scheduleAtFixedRate(timer,1000,1000);
    }
}