package com.latewind.lang.token;

public class Character extends BaseToken{
	private String value;
	
	public Character(String value) {
	this.value=value;
	}
	@Override
	public boolean isCharacter(){ return true;}
	
	public String getValue(){ return value;}
	
	@Override
	public String toString(){
		return value;
	}
}
