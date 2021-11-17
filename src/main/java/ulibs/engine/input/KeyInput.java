package main.java.ulibs.engine.input;

import java.util.Objects;

/**
 * Simple class containing character/id data. See {@link Inputs}
 * @author -Unknown-
 */
public class KeyInput implements IInput<KeyInput> {
	private final char character;
	private final int id;
	
	KeyInput(int id, char character) {
		this.character = character;
		this.id = id;
	}
	
	KeyInput(int id) {
		this.character = Character.MIN_VALUE;
		this.id = id;
	}
	
	/** @return True if this input has a printable character, otherwise false */
	public boolean hasCharacter() {
		return character != Character.MIN_VALUE;
	}
	
	/** @return The printable character this input may or may not have. Should use {@link KeyInput#hasCharacter()} to check if this input has a printable character! <br>
	 *  If this input has no printable character will return {@link Character#MIN_VALUE} */
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
