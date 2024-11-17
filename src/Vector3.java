public class Vector3 {
    public double x;
    public double y;
    public double z;
    public Vector3(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public double getLength(){
        return Math.sqrt(x*x+y*y+z*z);
    }
    public Vector3 normalised(){
        return divide(this,getLength());
    }
    public static Vector3 divide(Vector3 a,double b){
        return new Vector3(a.x/b,a.y/b,a.z/b);
    }
    public static Vector3 subtract(Vector3 a, Vector3 b){
        return new Vector3(a.x-b.x,a.y-b.y,a.z-b.z);
    }
    public static Vector3 multiply(Vector3 a, double b){
        return new Vector3(a.x*b,a.y*b,a.z*b);
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }
}
