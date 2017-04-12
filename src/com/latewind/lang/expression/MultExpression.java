package com.latewind.lang.expression;

import java.util.HashMap;

public class MultExpression implements Expression {
	private Expression leftExp;
	private Expression rightExp;

	public MultExpression(Expression leftExp, Expression rightExp) {
		this.leftExp = leftExp;
		this.rightExp = rightExp;
	}

	@Override
	public int interpret(){
		return leftExp.interpret() * rightExp.interpret();
	}

}
