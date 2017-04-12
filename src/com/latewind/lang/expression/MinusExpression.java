package com.latewind.lang.expression;

import java.util.HashMap;

public class MinusExpression implements Expression{
	private Expression leftExp;
	private Expression rightExp;

	public MinusExpression(Expression leftExp, Expression rightExp) {
		this.leftExp = leftExp;
		this.rightExp = rightExp;
	}

	@Override
	public int interpret(){

		return leftExp.interpret() /rightExp.interpret();
	}

}
