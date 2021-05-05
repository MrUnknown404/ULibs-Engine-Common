package main.java.ulibs.engine.utils;

import main.java.ulibs.engine.ClientBase;

/** An enum for screen tearing fixes. Used in {@link ClientBase#refreshFullscreen()}
 * @author -Unknown- */
@SuppressWarnings("javadoc")
public enum EnumScreenTearFix {
	off,
	vsync,
	experimental;
}
