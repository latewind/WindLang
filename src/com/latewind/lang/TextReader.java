package com.latewind.lang;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.latewind.lang.token.BaseToken;
import com.latewind.lang.token.Character;
import com.latewind.lang.token.Identifier;
import com.latewind.lang.token.Number;

/**
 * 
 * @author Latewind
 *
 */
public class TextReader {
	public static final String TEST_FILE = "D:/Wind-Lang.txt";
	public static final String patternStr = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
			+ "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
	private static Pattern patterner = Pattern.compile(patternStr);
	private LineNumberReader reader;
	private String lineBuf = "";

	private Queue<BaseToken> tokens = new LinkedList<>();

	public static TextReader newInstance(String filename) {

		return new TextReader(filename);
	}

	private TextReader() {
	}

	private TextReader(String fileName) {
		try {
			initReader(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("This file is not exist");
		}
	}         

	private void initReader(String fileName) throws FileNotFoundException {

		FileReader fileReader = new FileReader(fileName);
		reader = new LineNumberReader(fileReader);
	}

	public BaseToken read(){
		
		return tokens.poll();
	}
	
	public BaseToken peek(){
		
		return tokens.peek();
	}
	public void parse() throws IOException {
		while (hasMoreLine()) {
			parseLine();
		}
		System.out.println(tokens);
	}

	private boolean hasMoreLine() throws IOException {

		return (lineBuf = reader.readLine()) != null;
	}

	private String parseLine() {
		Matcher matcher = patterner.matcher(lineBuf);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int lineLength = lineBuf.length();
		System.out.println(lineBuf);
		for (int pos = 0; pos < lineLength;) {

			matcher.region(pos, lineLength);
			matcher.lookingAt();
			if (isNotSpace(matcher.group(1))) {
				GroupContext gc = new GroupContext();
				gc.setState(new CommentGroupState(matcher, gc));
			}
			pos = matcher.end();
		}

		return "";
	}

	class GroupContext {
		public void setState(BaseGroupState bg) {
			bg.done();
		}
	}

	abstract class BaseGroupState {
		protected Matcher matcher;
		protected GroupContext gc;

		public BaseGroupState(Matcher matcher, GroupContext gc) {
			this.matcher = matcher;
			this.gc = gc;
		}

		public abstract void done();
	}

	class CommentGroupState extends BaseGroupState {

		public CommentGroupState(Matcher matcher, GroupContext gc) {
			super(matcher, gc);
		}

		@Override
		public void done() {
			if (isComment(matcher.group(2))) {
				// do nothing
			} else {
				gc.setState(new NumberGroupState(matcher, gc));
			}
		}
	}

	class NumberGroupState extends BaseGroupState {
		
		public NumberGroupState(Matcher matcher, GroupContext gc) {
			super(matcher, gc);
		}

		@Override
		public void done() {
			if (isNumber(matcher.group(3))) {
				addToken(new Number(Integer.valueOf(matcher.group(3))));
			} else {
				gc.setState(new CharacterGroupState(matcher, gc));
			}

		}

	}

	class CharacterGroupState extends BaseGroupState {

		public CharacterGroupState(Matcher matcher, GroupContext gc) {
			super(matcher, gc);
		}

		@Override
		public void done() {
			if (isCharacter(matcher.group(4))) {
				addToken(new Character(matcher.group(4)));
			} else {
				addToken(new Identifier(matcher.group(1)));
			}

		}

	}

	private boolean isCharacter(String group3) {
		return StringUtils.isNotBlank(group3);
	}

	private boolean isNumber(String group2) {
		return StringUtils.isNotBlank(group2);
	}

	private boolean isComment(String group2) {
		return StringUtils.isNotBlank(group2);
	}

	private boolean isNotSpace(String group1) {
		return StringUtils.isNotBlank(group1);
	}

	private void addToken(BaseToken token) {

		tokens.add(token);
	}

	public static void main(String[] args) throws IOException {

		TextReader.newInstance(TEST_FILE).parse();

	}
}
