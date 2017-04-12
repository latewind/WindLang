package com.latewind.lang.demo;

import java.io.IOException;
import java.util.Objects;

import com.latewind.lang.TextReader;
import com.latewind.lang.expression.ExpFactory;
import com.latewind.lang.expression.Expression;
import com.latewind.lang.expression.NumberExpression;
import com.latewind.lang.token.BaseToken;
import com.latewind.lang.token.Identifier;
import com.latewind.lang.token.Number;
/**
 * 
 * @author Late Wind
 *
 * factor:: 	Number | "(" expression ")"
 * term::   	factor {"*" | "/" factor}
 * expression:: term {"+" | "-" term}
 *  
 *
 *
 */
public class SimpleParse {
	static TextReader textReader=TextReader.newInstance("D:/calcu.txt");
	static{
	try {
		textReader.parse();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		SimpleParse simpleParse = new SimpleParse();
		Expression result=simpleParse.expresssion();
		System.out.println(result.interpret());
	}

	public Expression expresssion(){
		
		Expression leftExp=term();
		while(isIdentifier("+")||isIdentifier("-")){
			BaseToken symbol = textReader.read();
			leftExp =ExpFactory.newExpression(leftExp, symbol.toString(), term());
			
			
		}
		
		return leftExp;
	}

	private Expression term() {
		Expression leftExp=factor();
		while(isIdentifier("*")||isIdentifier("/")){
			BaseToken symbol = textReader.read();
			leftExp =ExpFactory.newExpression(leftExp, symbol.toString(), factor());
		}
		
		return leftExp;
	}
	
	private Expression factor(){
		
		if(isNumber()){
			return new NumberExpression((Number) (textReader.read()));
		}
		
		if(isIdentifier("(")){
			skip("(");
			Expression exp=expresssion();
			skip(")");
			
			return exp;
		}
		return null;
		
	}

	private boolean isIdentifier(String symbolStr) {
		
		BaseToken token=textReader.peek();
		if(token instanceof Identifier && Objects.equals(symbolStr, ((Identifier) token).getName())){
			
			return true;
		}
		
		return false;
	}

	private boolean isNumber() {
		return textReader.peek() instanceof Number;
	}
	
	private void skip(String symbol) {
//		if(!Objects.equals(textReader.read().toString(), symbol)){
//			throw new ParseException("parse error , maybe is the"+symbol);
//			
//		}
		textReader.read();
		
	}
}
