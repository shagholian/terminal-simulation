import java.util.Scanner; 
public class Cp{
    boolean overwriteAll;
    public boolean cp(Env env, String stmt){
        String[] arg = stmt.split(" ", 0);
        if(arg.length != 3){
            System.out.println("cp: Arguments is not approved");
            return false;
        }
        Node sourceNode = env.wd.findByPath(arg[1]);
        if(sourceNode == null) {
            System.out.println("cp: No such file or directory: " + arg[1]);
            return false;
        }
        String newName = sourceNode.name;
        Node secondNode = env.wd.findByPath(arg[2]);
        if(secondNode == null) {
            Node newNode = new Node();
            newName = newNode.dirName(arg[2]);
		    secondNode = env.wd.findByPath(newNode.parentsName(arg[2]));
        }
        if(secondNode == null) {
            System.out.println("cp: No such file or directory: " + arg[2]);
            return false;
        }
        if(secondNode.type != 1){
            System.out.println("cp: omitting directory: " + arg[2]);
            return false;
        }
        Directory distDir = (Directory) secondNode;
        overwriteAll = false;
        boolean change = false;
        if (copyNode(sourceNode, newName, distDir) != 0) {
            return change;
        }
        change = true;
        if (sourceNode.type == 1) {
            Directory srcDir = (Directory ) sourceNode;
            for (Node node : srcDir.getChildrenRecursive()) {
                if (copyNode(node, newName + "/" + node.getPath(srcDir), distDir) != 0) {
                    return change;
                }
                change = true;
            }
        }
        return change;
    }

    int copyNode(Node node, String relativePath, Directory distDir) {
        Node pathInDist = distDir.findByPath(relativePath);
        if (pathInDist != null) {
            if (node.type != pathInDist.type) {
                System.out.println("cp: cannot overwrite directory '" + relativePath + "' with non-directory");
                return 1;
            }
            char overwrite = overwriteAll ? 'y' : askForOverwrite(relativePath);
            if (overwrite == 'n') {
                System.out.println("cancelled");
                return 1;
            }
            if (overwrite == 'a') {
                overwriteAll = true;
            }
        } else {
            Node newNode = new Node();
            String basename = newNode.dirName(relativePath);
            String dirname = newNode.parentsName(relativePath);
            Directory parent = (Directory) distDir.findByPath(dirname);
            Node newN;
            if (node.type == 1) {
                newN = new Directory(basename);
            } else {
                newN = new File(basename);
            }
            newN.setParent(parent);
            parent.addChild(newN);
        }
        return 0;
    }
    
    char askForOverwrite(String path) {
        do {
            System.out.println("cp: overwrite : " + path + "? [y=Yes, n=No, a=All]: ");
            Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();  
            if (str == "n" || str == "y" || str == "a") {
                return str.charAt(0);
            }
        } while(true);
    }
}