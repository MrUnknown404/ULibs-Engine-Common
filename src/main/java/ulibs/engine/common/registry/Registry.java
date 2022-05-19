package main.java.ulibs.engine.common.registry;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;

public abstract class Registry<T extends IRegistrable> {
	private final Map<String, T> all = new LinkedHashMap<String, T>();
	
	public abstract void register();
	
	protected void add(T value) {
		if (all.containsKey(value.getKey())) {
			Console.print(WarningType.Warning, "Tried to register something in " + getClass().getSimpleName() + " Registry that already exists!");
			return;
		}
		
		all.put(value.getKey(), value);
		Console.print(WarningType.RegisterDebug, "Registered '" + value.getKey() + "' for " + getClass().getSimpleName() + " Registry!");
	}
	
	public Map<String, T> getAll() {
		return all;
	}
	
	public T getByKey(String key) {
		return all.get(key);
	}
	
	public Collection<T> getValues() {
		return all.values();
	}
}
