package com.latewind.lang.expression;

public class ExpFactory {
	
	public static Expression   newExpression(Expression leftExp,String optSymbol,Expression rightExp){
		
		switch (optSymbol) {
		case "+":
			return new PlusExpression(leftExp, rightExp);
		case "-":
			return new MinusExpression(leftExp, rightExp);
		case "*":
			return new MultExpression(leftExp, rightExp);
		case "/":
			return new DivExpression(leftExp, rightExp);
		default:
			break;
		}
		
		return rightExp;
		
	}

}
