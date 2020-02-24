public class Ls {
    public void list(Env env, String path){
        for (Node node : env.wd.children) {
            if (node.type == 1) {
                System.out.println(node.getName() + "/");
            }
        }
        for (Node node: env.wd.children) {
            if (node.type == 0) {
                System.out.println(node.getName());
            }
        }

    }
}