import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Main {
    public static BufferedImage zBuffer=new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
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
        player.position=new Vector3(4,0,4);
        player.rotation=new Vector3(0,3*Math.PI/4,0);
        player.scale=new Vector3(800,600,0);
        JFrame mainF=new JFrame();

        Mesh cube=new Mesh("src/cube.obj");
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                List<List<int[]>> a=player.renderMesh(cube);
                List<List<Vector3>> aB=player.renderMeshPoints(cube);
                Graphics g2=zBuffer.getGraphics();
                /*
                for(int i=0; i<a.size(); i++) {
                    for(int x=0; x<800; x++){
                        for(int y=0; y<600; y++) {
                            Vector3 p1=new Vector3(a.get(i).get(0)[0],a.get(i).get(1)[0],0);
                            Vector3 p2=new Vector3(a.get(i).get(0)[1],a.get(i).get(1)[1],0)
                            if(PointInTriangle(
                                    new Vector3(x,y,0),
                                    new Vector3(a.get(i).get(0)[0],a.get(i).get(1)[0],0),
                                    ,
                                    new Vector3(a.get(i).get(0)[2],a.get(i).get(1)[2],0))){
                                double total=0;
                                double d1=Math.sqrt(x-);
                            }
                        }
                    }
                    //img.setRGB(x, y, Color.RED.getRGB());
                }
                */
                /*
                draws points on the canvas
                 */
                for(int i=0; i<a.size(); i++) {
                    Polygon poly = new Polygon(a.get(i).get(0), a.get(i).get(1), a.get(i).get(0).length);
                    Color t=new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255));
                    g.setColor(t);
                    g.drawPolygon(poly);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };
        //makes render visable
        mainF.getContentPane().add(p);
        mainF.setSize(800, 600);
        mainF.setVisible(true);
    }
}