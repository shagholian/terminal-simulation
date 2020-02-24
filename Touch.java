
public class Touch {
    public boolean touchfile(Env env, String stmt){
        String[] arg = stmt.split(" ", 0);
        if(arg.length != 2){
            System.out.println("touch: Arguments is not approved");
            return false;
        }

        Node node = new Node();
        String parentsName = node.parentsName(arg[1]);
        String dirName = node.dirName(arg[1]);
        Node parent = env.wd.findByPath(parentsName);

        if(parent == null) {
            System.out.println("mkdir: cannot create directory ‘" + arg[1] + "‘ : No such file or directory");
            return false;
        }
        if(parent.type != 1){
            System.out.println("mkdir: cannot create directory " +  arg[1] + ": No a directory");
            return false;
        }
        Directory dir = (Directory) parent;
        Node newItem = dir.findByPath(dirName);
        if (newItem != null) {
            return false;
        }
        File newFile = new File(dirName);

        dir.addChild(newFile);
        return true;
    }
}