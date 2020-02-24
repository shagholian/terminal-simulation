public class Rmdir {
    public boolean removeDir(Env env, String stmt) {
        String[] arg = stmt.split(" ", 0);
        if(arg.length < 2){
            System.out.println("rm: Arguments is not approved");
            return false;
        }
        Node item = env.wd.findByPath(arg[1]);
        if (item == null) {
            System.out.println("rmdir: No such file or directory: "  + arg[1]);
            return false;
        }
        if (item.type !=1) {
            System.out.println("rmdir: failed to remove " + arg[1] + ": Not a directory");
            return false;
        }
        Directory p = (Directory) item;
        if (!p.empty()) {
            System.out.println("rmdir: Directory is not empty: " + arg[1]);
            return false;
        }
        p = env.wd;
        do {
            if (p == item) {
                System.out.println("rmdir: Cannot remove directory in use: " + arg[1]);
                return false;
            }
            p = p.getParent();
        } while (p != null);

        Directory parent = item.getParent();
        parent.removeChild(item);
        return true;
    }
}