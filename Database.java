
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*; 

public class Database {

    private JSONObject save(Node node) {
        JSONObject obj = new JSONObject(); 
          
        obj.put("type", node.type); 
        obj.put("name", node.name);
        return obj;
    }
    private JSONObject save(Directory dir) {
        JSONObject obj = save((Node) dir);
        JSONArray children = new JSONArray();
        for (Node child : dir.children) {
            children.add(save(child));
        }
        obj.put("children", children);
        return obj;
    }

    private JSONObject save(File file) {
        return save((Node) file);
    }

    public void write(Directory root) {
        try {
            PrintWriter pw = new PrintWriter("db.json"); 
            pw.write(save(root).toJSONString());
            pw.flush(); 
            pw.close(); 
        } catch (FileNotFoundException e) {
        }
    }
    public Directory read() {
        try {
            Object obj = new JSONParser().parse(new FileReader("db.json")); 
            JSONObject jo = (JSONObject) obj; 
            return (Directory) load(jo);
        } catch (IOException e) {
        } catch (ParseException e) {
        }
        return null;
    }
    private Node load(JSONObject obj) {
        long type = (long) obj.get("type");
        if (type == 1) {
            Directory node = new Directory((String)obj.get("name"));
            JSONArray children = (JSONArray) obj.get("children");
            if (children != null) {
                for (Object childObj : children) {
                    Node child = load((JSONObject) childObj); 
                    node.addChild(child);
                }
            }
            return node;
        }
        File node = new File((String) obj.get("name"));
        return node;
    }
}