import java.lang.*;
import java.util.ArrayList;

public class Node {
    public String name;
    public int type;
    public Directory parent;

    public Node(String name, int type) {
        this.name = name;
        this.type = type;
    }
    public Node(){
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String directoryName(){
        return (this.parent != null) ? this.parent.getPath() : "";
    }

    public void setParent(Directory dir){
        this.parent = dir;
    }

    public Directory getParent(){
        return this.parent;
    }

    public String getPath(){
        return getPath(null);
    }

    public String getPath(Directory dir){
        if(dir == this){
            return "";
        }
        if(this.parent == null) {
            return "/";
        }
        String path = this.parent.getPath(dir);
        StringBuilder str = new StringBuilder(path);
        if(!path.equals("") && path.charAt(path.length() - 1) != '/'){
            str.insert(path.length(), '/');
        }
        str.append(this.name);
        path = str.toString();
        return path;
    }
    public String parentsName(String file){
        String[] s = file.split("/", 0);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String str : s) {
            arrayList.add(str);
        }
        arrayList.remove(arrayList.size()-1);
        String listString = "";
        for (int x = 0;x < arrayList.size(); x++) {
            if (x != 0) {
                listString += "/";
            }
            listString += arrayList.get(x);
        }
        return listString;
    }

    public String dirName(String file){
        String[] s = file.split("/", 0);
        return s[s.length-1];
    }
}