/**
 * a point in 3d space with x vars
 * x
 * y - up
 * z
 */
public class Vector3 {
    public double x;
    public double y;
    public double z;

    /**
     * @param x the x value for the point
     * @param y the y value for the point
     * @param z the z value for the point
     */
    public Vector3(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * gets the distance that the vector is from (0,0,0)
     * @return the distance in a double
     */
    public double getLength(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    /**
     * returns a normalised version of the vector
     * @return the normalised vector
     */
    public Vector3 normalised(){
        return divide(this,getLength());
    }

    /**
     * divides a vector by a double
     * @param a a vector
     * @param b a double
     * @return a / b
     */
    public static Vector3 divide(Vector3 a,double b){
        return new Vector3(a.x/b,a.y/b,a.z/b);
    }
    /**
     * subtract two vectors
     * @param a a vector
     * @param b a vector
     * @return a - b
     */
    public static Vector3 subtract(Vector3 a, Vector3 b){
        return new Vector3(a.x-b.x,a.y-b.y,a.z-b.z);
    }
    /**
     * divides two vectors
     * @param a a vector
     * @param b a vector
     * @return a / b
     */
    public static Vector3 divide(Vector3 a, Vector3 b){
        return new Vector3(a.x/b.x,a.y/b.y,a.z/b.z);
    }
    /**
     * multiples a vector by a double
     * @param a a vector
     * @param b a double
     * @return a * b
     */
    public static Vector3 multiply(Vector3 a, double b){
        return new Vector3(a.x*b,a.y*b,a.z*b);
    }
    /**
     * multiplys two vectors
     * @param a a vector
     * @param b a vector
     * @return a * b
     */
    public static Vector3 multiply(Vector3 a, Vector3 b){
        return new Vector3(a.x*b.x,a.y*b.y,a.z*b.z);
    }
    /**
     * adds two vectors
     * @param a a vector
     * @param b a vector
     * @return a + b
     */
    public static Vector3 add(Vector3 a, Vector3 b){
        return new Vector3(a.x+b.x,a.y+b.y,a.z+b.z);
    }
    /**
     * gets the dot product of 2 vectors
     * @param a a vector
     * @param b a vector
     * @return a . b
     */
    public static double dot(Vector3 a, Vector3 b){
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }
}
