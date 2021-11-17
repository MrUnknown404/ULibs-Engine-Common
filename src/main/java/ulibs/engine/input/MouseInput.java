package main.java.ulibs.engine.input;

import java.util.Objects;

public class MouseInput implements IInput<MouseInput> {
	private final int id;
	
	public MouseInput(int id) {
		this.id = id;
	}
	
	@Override
	public boolean is(MouseInput input) {
		return input.id == id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof MouseInput)) {
			return false;
		}
		
		return is((MouseInput) obj);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String toString() {
		switch (id) {
			case 1:
				return "Mouse: '1' (Left)";
			case 2:
				return "Mouse: '2' (Right)";
			case 3:
				return "Mouse: '3' (Middle)";
			default:
				return "Mouse: '" + id + "'";
		}
	}
}
