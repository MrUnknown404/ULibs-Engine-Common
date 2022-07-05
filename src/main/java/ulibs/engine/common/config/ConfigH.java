package main.java.ulibs.engine.common.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.common.CommonBase;
import main.java.ulibs.engine.common.CommonBase.LoadingState;

public class ConfigH {
	public static final Map<String, ConfigData<?>> DEFAULT_MAP = new HashMap<String, ConfigData<?>>();
	
	public static <T extends Config> void registerConfig(T defaultConfig, Consumer<T> setConfig) {
		if (CommonBase.isHappeningAfter(LoadingState.CONFIG)) {
			Console.print(WarningType.Warning, "Added a config after #init. This could be bad?");
		}
		
		DEFAULT_MAP.put(defaultConfig.getName(), new ConfigData<T>(defaultConfig, setConfig));
		Console.print(WarningType.RegisterDebug, "Registered new config named '" + defaultConfig.getName() + "'!");
	}
	
	public static void loadConfigs() {
		Console.print(WarningType.Info, "Loading " + DEFAULT_MAP.size() + " configs");
		
		for (Entry<String, ConfigData<?>> pair : DEFAULT_MAP.entrySet()) {
			String name = pair.getKey();
			ConfigData<?> data = pair.getValue();
			
			File f = new File(CommonBase.getJarLocation() + "/configs/" + name + ".cfg");
			
			if (!f.exists()) {
				Console.print(WarningType.Info, "No config named '" + name + "' found! Creating now!");
				try (FileWriter fw = new FileWriter(f)) {
					CommonBase.getGson().toJson(data.defaultConfig, fw);
					fw.flush();
					data.setConfig(data.defaultConfig);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try (FileReader fr = new FileReader(f)) {
				data.setConfig(CommonBase.getGson().fromJson(fr, data.defaultConfig.getClass()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class ConfigData<T extends Config> {
		private final T defaultConfig;
		private final Consumer<T> setConfig;
		
		private ConfigData(T defaultConfig, Consumer<T> setConfig) {
			this.defaultConfig = defaultConfig;
			this.setConfig = setConfig;
		}
		
		@SuppressWarnings("unchecked")
		public void setConfig(Config config) {
			setConfig.accept((T) config);
		}
	}
}
