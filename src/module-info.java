module ulibs.engine.common {
	exports main.java.ulibs.engine.common;
	exports main.java.ulibs.engine.common.config;
	exports main.java.ulibs.engine.common.registry;
	exports main.java.ulibs.engine.common.utils;
	
	requires transitive com.google.gson;
	requires transitive ulibs.common;
}