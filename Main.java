import java.util.Scanner; 
public class Main {
	Touch touch = new Touch();
	public static void main(String args[]){
		Database db = new Database();
		Directory rootDir = db.read();
		if (rootDir == null) {
			rootDir = new Directory();
		}
		Env env = new Env(rootDir);
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.print(env.wd.getPath() + "# "); 
			String input = sc.nextLine();  
			String[] splitInput = input.split(" ", 0);
			String cmd = splitInput[0];
			boolean result = false;
			switch(cmd) {
				case "touch" : 
					Touch touch = new Touch();
					result = touch.touchfile(env, input);
					break;
				case "rm" : 
					Rm rm = new Rm();
					result = rm.remove(env, input);
					break;
				case "cp" : 
					Cp cp = new Cp();
					result = cp.cp(env, input);
					break;
				case "mv" : 
					Mv mv = new Mv();
					result = mv.moveRename(env, input);
					break;
				case "ls" : 
					Ls ls = new Ls();
					ls.list(env, input);
					break;
				case "mkdir" : 
					Mkdir mkdir = new Mkdir();
					result = mkdir.createDir(env, input);
					break;
				case "rmdir" :
					Rmdir rmdir = new Rmdir();
					result = rmdir.removeDir(env, input); 
					break;
				case "cd" :
					Cd cd = new Cd();
					cd.changePath(env, input);
					break;
				case "pwd" :
					new Pwd(env, input);
					break;
				default :  
					System.out.println("command not found");
			}
			db.write(rootDir);
		}
	}
}
