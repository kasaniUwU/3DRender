import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mesh {
    public List<Vector3> points =new ArrayList<>();
    public List<List<Integer>> faces =new ArrayList<>();
    public Mesh(String fileString){
        try {
            File file = new File(fileString);
            Scanner fileIn = new Scanner(file);
            while (fileIn.hasNextLine()) {
                String data = fileIn.nextLine();
                if(data.substring(0,2).equals("v ")){
                    String temp=data.substring(2);
                    double x= Double.parseDouble(temp.substring(0,temp.indexOf(" ")));
                    temp=temp.substring(temp.indexOf(" ")+1);
                    double y= Double.parseDouble(temp.substring(0,temp.indexOf(" ")+1));
                    temp=temp.substring(temp.indexOf(" ")+1);
                    double z= Double.parseDouble(temp);

                    points.add(new Vector3(x,y,z));
                    //System.out.println(new Vector3(x,y,z));
                }else if(data.substring(0,2).equals("f ")){
                    String temp=data.substring(1);
                    List<Integer> tempList=new ArrayList<>();
                    while(temp.indexOf(" ")!=-1) {
                        tempList.add(Integer.valueOf(temp.substring(temp.indexOf(" ")+1,temp.indexOf("/"))));
                        temp=temp.substring(1);
                        if(temp.indexOf(" ")!=-1) {
                            temp = temp.substring(temp.indexOf(" "));
                        }
                    }
                    tempList.add(Integer.valueOf(temp.substring(0,temp.indexOf("/"))));
                    faces.add(tempList);
                }
            }
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("file \""+fileString+"\" does not exist");
        }
    }
}
