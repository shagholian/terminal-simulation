public class Rm {
    public boolean remove(Env env, String stmt) {
        String[] arg = stmt.split(" ", 0);
        if(arg.length < 2){
            System.out.println("rm: Arguments is not approved");
            return false;
        }

        Node nodeItem = env.wd.findByPath(arg[1]);
        if (nodeItem == null) {
            System.out.println("rm: No such file or directory: " + arg[1]);
            return false ;
        }
        if (nodeItem.type == 1) {
            Directory p = (Directory) nodeItem;
            if (!p.empty()) {
                System.out.println("rm: Directory is not empty: " + arg[1]);
                return false;
            }
            p = env.wd;
            do {
                if (p == nodeItem) {
                    System.out.println("rm: Cannot remove directory in use: " + arg[1]);
                    return false;
                }
                p = p.getParent();
            } while (p != null);
        }
        Directory parent = nodeItem.getParent();
        parent.removeChild(nodeItem);
        return true;
    }
}