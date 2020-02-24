
public class Cd {
    public void changePath(Env env, String stmt) {
        String[] arg = stmt.split(" ", 0);
        if(arg.length != 2){
            System.out.println("cd: Arguments is not approved");
            return;
        }
        Node rootItem = env.wd.findByPath(arg[1]);
        if(rootItem == null){
            System.out.println("cd: " + arg[1] + ": No such file or directory");
            return;
        }
        if(!(rootItem instanceof Directory)){
            System.out.println("cd: not a directory: " +  arg[1]);
            return;
        }
        env.wd = (Directory) rootItem;
    }
}