package com.latewind.lang.token;

public class Number extends BaseToken {
	private Integer value;

	public Number(Integer value) {
		this.value=value;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	public Integer getValue() {
		return value;
	}
	
	@Override
	public String toString(){
		return value+"";
	}
}
