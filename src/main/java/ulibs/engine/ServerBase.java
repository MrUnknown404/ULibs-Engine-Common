package main.java.ulibs.engine;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.ulibs.common.utils.Console.WarningType;

public abstract class ServerBase extends CommonBase {
	
	protected ServerBase(String title, String internalTitle, boolean isDebug, int logCount, WarningType[] warnings) {
		super(title, internalTitle, isDebug, logCount, warnings);
	}
	
	@Override
	protected final boolean preRun() {
		java.io.Console console = System.console();
		if (console == null && !GraphicsEnvironment.isHeadless()) {
			String filename = ServerBase.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
			if (filename.endsWith("/")) { //Eclipse fix
				return true;
			}
			
			try { //TODO replace this just a single command instead of making a batch file
				File batch = new File(title + ".bat");
				if (!batch.exists()) {
					batch.createNewFile();
				}
				
				PrintWriter writer = new PrintWriter(batch);
				writer.println("@echo off");
				writer.println("title " + title);
				writer.println("java -jar " + filename);
				writer.println("pause");
				writer.println("exit");
				writer.flush();
				writer.close();
				
				Runtime.getRuntime().exec("cmd /c start \"\" " + batch.getPath());
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	protected final void internalRender() {
		
	}
}
