package main.java.ulibs.engine.input;

import java.util.Objects;

public class KeyInput implements IInput<KeyInput> {
	private final char character;
	public final int id;
	
	public KeyInput(int id, char character) {
		this.character = character;
		this.id = id;
	}
	
	public KeyInput(int id) {
		this.character = Character.MIN_VALUE;
		this.id = id;
	}
	
	public boolean hasCharacter() {
		return character != Character.MIN_VALUE;
	}
	
	public char getCharacter() {
		return character;
	}
	
	@Override
	public boolean is(KeyInput input) {
		return input.id == id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof KeyInput)) {
			return false;
		}
		
		return is((KeyInput) obj);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String toString() {
		return "Key: " + "'" + (hasCharacter() ? String.valueOf(getCharacter()) : id) + "'";
	}
}
