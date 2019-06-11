package com.quellek.rotatocraft.blocks;

import net.minecraft.util.IStringSerializable;

public enum SpinnerState implements IStringSerializable{
	OFF("off"),
	WORKING("working");
	
	public static final SpinnerState[] VALUES = SpinnerState.values();
	
	private final String name;
	
	SpinnerState(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
