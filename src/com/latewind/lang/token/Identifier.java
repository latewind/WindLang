package com.latewind.lang.token;

public class Identifier extends BaseToken {
	private String name;
	public Identifier(String name) {
		this.name=name;
	}
	@Override
	public boolean isIdentifier(){ return true;}
	public String getName(){ return name;}
	
	@Override
	public String toString(){
		return name;
	}

}
