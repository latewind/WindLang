package com.latewind.lang.expression;

import java.util.HashMap;

import com.latewind.lang.token.Number;

public class NumberExpression implements Expression{
	private Number number;
	public NumberExpression(Number number) {
		this.number=number;
	}
	@Override
	public int interpret() {
		return number.getValue();
	}
}
