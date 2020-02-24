public class Mv {
    public boolean moveRename(Env env, String stmt){
        String[] arg = stmt.split(" ", 0);
        if(arg.length < 3){
            System.out.println("mv: Arguments is not approved");
            return false;
        }
        Node sourceNode = env.wd.findByPath(arg[1]);
        if (sourceNode == null) {
            System.out.println("mv: Cannot move: No such file or directory: " + arg[1]);
            return false;
        }
        String newName = sourceNode.name;
        Node distParentNode = env.wd.findByPath(arg[2]);
        if (distParentNode == null) {
            Node node = new Node();
            newName = node.dirName(arg[2]);
            distParentNode = env.wd.findByPath(node.parentsName(arg[2]));
        }
        if (distParentNode == null) {
            System.out.println("mv: Cannot move: No such file or directory: " + arg[2]);
            return false;
        }
        if (distParentNode.type != 1) {
            System.out.println("mv: Cannot move: Not a directory: " + arg[2]);
            return false;
        }

        Node p = distParentNode;
        do {
            if (p == sourceNode) {
                System.out.println("rm: Cannot move directory to it's child: " + arg[1]);
                return false;
            }
            p = p.getParent();
        } while (p != null);


        Directory distDir = (Directory) distParentNode;
        Node distNode = distDir.findByName(newName);
        if (distNode != null) {
            System.out.println("mv: Cannot move: File exists: " + arg[2]);
            return false;
        }
        sourceNode.getParent().removeChild(sourceNode);
        sourceNode.setParent(distDir);
        sourceNode.setName(newName);
        distDir.addChild(sourceNode);
        return true;
    }
}