import java.util.*;
public class Directory extends Node {
    ArrayList<Node> children;

    public Directory(){
        super("", 1);
        children = new ArrayList<Node>();
    }

    public Directory(String name){
        super(name, 1);
        children = new ArrayList<Node>();
    }
    public boolean empty(){
        return this.children.size() == 0;
    }

    public void addChild(Node child){
        child.parent = this;
        children.add(child);
    }

    public void removeChild(Node child){
        for (Node tmp : this.children){
            if(tmp == child){
                this.children.remove(children.indexOf(tmp));
                return;
            }
        }
    }

    public ArrayList<Node> getChildren(){
        return this.children;
    }

    public ArrayList<Node> getChildrenRecursive() {
        ArrayList<Node> arrtmp = new ArrayList<Node>();
        for (Node node: this.children) {
            arrtmp.add(node);
            if (node.type == 1) {
                Directory dir = (Directory) node;
                for (Node child: dir.getChildrenRecursive()) {
                    arrtmp.add(child);
                }
            }
        }
        return arrtmp;
    }

    public Node findByPath(String path){
        for (int x = path.length() - 1; x > 0 && path.charAt(x) == '/'; x--) {
            path = path.substring(0, x);
        }
        int slash = path.indexOf("/");
        if(slash == 0){
            if(this.parent != null){
                return this.parent.findByPath(path);
            }else {
                path = path.substring(1);
                slash = path.indexOf("/");
            }
        }
        Node found = null;
        String childrenName = slash == -1 ? path : path.substring(0, slash);
        if(childrenName.equals("..")){
            found = this.parent;
        }else {
            found = findByName(path);
        }
        if(found == null){
            return null;
        }
        if(slash == -1){
            return found;
        }
        if (found.type == 1) {
            Directory dir = (Directory) found;
            return dir.findByPath(path.substring(slash + 1));
        }
        if (path.length() > slash) { 
            return null;
        }
        return found;
    }

    public Node findByName(String name){
        if(name.equals("") || name.equals(".")){
            return this;
        }
        for(Node child: children){
            if(child.name.equals(name)){
                return child;
            }
        }
        return null;
    }
}