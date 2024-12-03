/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wisii.wisedoc.banding.io.dtd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import com.wisii.wisedoc.exception.NorootElementException;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */

public class DTDParser
{

	protected Scanner scanner;

	protected DTD dtd;

	protected Object defaultLocation;

	/** Creates a parser that will read from the specified Reader object */
	public DTDParser(Reader in)
	{
		scanner = new Scanner(in, false);
		dtd = new DTD();
	}

	/** Creates a parser that will read from the specified File object */
	public DTDParser(File in) throws IOException
	{
		defaultLocation = in.getParentFile();
		scanner = new Scanner(new BufferedReader(new FileReader(in)), false);
		dtd = new DTD();
	}

	/** Creates a parser that will read from the specified URL object */
	public DTDParser(URL in) throws IOException
	{
		String file = in.getFile();
		defaultLocation = new URL(in.getProtocol(), in.getHost(), in.getPort(),
				file.substring(0, file.lastIndexOf('/') + 1));

		scanner = new Scanner(new BufferedReader(new InputStreamReader(in
				.openStream())), false);
		dtd = new DTD();
	}

	/**
	 * Parses the DTD file and returns a DTD object describing the DTD.
	 * 
	 * @param guessRootElement
	 *            If true, tells the parser to try to guess the root element of
	 *            the document by process of elimination
	 */
	public DTD parse() throws IOException
	{
		Token token;
		for (;;)
		{
			token = scanner.peek();
			if (token.type == Scanner.EOF)
				break;
			try
			{
				parseTopLevelElement();
			} catch (NorootElementException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Hashtable roots = new Hashtable();
		Enumeration e = dtd.elements.elements();
		while (e.hasMoreElements())
		{
			DTDElement element = (DTDElement) e.nextElement();
			roots.put(element.name, element);
		}
		e = dtd.elements.elements();
		while (e.hasMoreElements())
		{
			DTDElement element = (DTDElement) e.nextElement();
			if (!(element.content instanceof DTDContainer))
				continue;

			Enumeration items = ((DTDContainer) element.content).getItemsVec()
					.elements();

			while (items.hasMoreElements())
			{
				removeElements(roots, dtd, (DTDItem) items.nextElement());
			}
		}
		if (roots.size() == 1)
		{
			e = roots.elements();
			dtd.rootElement = (DTDElement) e.nextElement();
		} else
		{
			dtd.rootElement = null;
		}
		return dtd;
	}

	protected void removeElements(Hashtable h, DTD dtd, DTDItem item)
	{
		if (item instanceof DTDName)
		{
			h.remove(((DTDName) item).value);
		} else if (item instanceof DTDContainer)
		{
			Enumeration e = ((DTDContainer) item).getItemsVec().elements();

			while (e.hasMoreElements())
			{
				removeElements(h, dtd, (DTDItem) e.nextElement());
			}
		}
	}

	protected void parseTopLevelElement() throws IOException,
			NorootElementException
	{
		Token token = scanner.get();

		if (token.type == Scanner.CONDITIONAL)
		{
			token = expect(Scanner.IDENTIFIER);

			if (token.value.equals("IGNORE"))
			{
				scanner.skipConditional();
			} else
			{
				if (token.value.equals("INCLUDE"))
				{
					scanner.skipUntil('[');
				} else
				{
					throw new IOException();
				}
			}
		} else if (token.type == Scanner.ENDCONDITIONAL)
		{
			// Don't need to do anything for this token
		} else if (token.type == Scanner.COMMENT)
		{
			dtd.items.addElement(new DTDComment(token.value));
		} else if (token.type == Scanner.LTBANG)
		{
			token = expect(Scanner.IDENTIFIER);

			if (token.value.equals("ELEMENT"))
			{
				parseElement();
			} else if (token.value.equals("ATTLIST"))
			{
				parseAttlist();
			} else
			{
				skipUntil(Scanner.GT);
			}
		} else
		{
			throw new NorootElementException("您选择的DTD文件格式不正确！");
		}

	}

	protected void skipUntil(TokenType stopToken) throws IOException
	{
		Token token = scanner.get();

		while (token.type != stopToken)
		{
			token = scanner.get();
		}
	}

	protected Token expect(TokenType expected) throws IOException
	{
		Token token = scanner.get();
		return token;
	}

	protected void parseElement() throws IOException
	{
		Token name = expect(Scanner.IDENTIFIER);
		DTDElement element = (DTDElement) dtd.elements.get(name.value);
		if (element == null)
		{
			element = new DTDElement(name.value);
			dtd.elements.put(element.name, element);
		} else if (element.content != null)
		{

			throw new IOException();
		}
		dtd.items.addElement(element);
		parseContentSpec(scanner, element);
		expect(Scanner.GT);
	}

	protected void parseContentSpec(Scanner scanner, DTDElement element)
			throws IOException
	{
		Token token = scanner.get();

		if (token.type == Scanner.IDENTIFIER)
		{
			if (token.value.equals("EMPTY"))
			{
				element.content = new DTDEmpty();
			} else
			{
				throw new IOException();
			}
		} else if (token.type == Scanner.LPAREN)
		{
			token = scanner.peek();

			if (token.type == Scanner.IDENTIFIER)
			{
				if (token.value.equals("#PCDATA"))
				{
					parseMixed(element);
				} else
				{
					parseChildren(element);
				}
			} else if (token.type == Scanner.LPAREN)
			{
				parseChildren(element);
			}
		}
	}

	protected void parseMixed(DTDElement element) throws IOException
	{

		boolean isPcdataOnly = true;

		DTDMixed mixed = new DTDMixed();

		scanner.get();

		element.content = mixed;

		for (;;)
		{
			Token token = scanner.get();

			if (token.type == Scanner.RPAREN)
			{
				token = scanner.peek();

				if (token.type == Scanner.ASTERISK)
				{
					scanner.get();
					mixed.cardinal = DTDCardinal.ZEROMANY;
				} else
				{
					if (!isPcdataOnly)
					{
						throw new IOException();
					}

					mixed.cardinal = DTDCardinal.NONE;
				}

				return;
			} else if (token.type == Scanner.PIPE)
			{
				token = scanner.get();

				mixed.add(new DTDName(token.value));

				// MAW Ver. 1.19
				isPcdataOnly = false;
			} else
			{
				throw new IOException();
			}
		}
	}

	protected void parseChildren(DTDElement element) throws IOException
	{
		DTDContainer choiceSeq = parseChoiceSequence();

		Token token = scanner.peek();

		choiceSeq.cardinal = parseCardinality();

		if (token.type == Scanner.QUES)
		{
			choiceSeq.cardinal = DTDCardinal.OPTIONAL;
		} else if (token.type == Scanner.ASTERISK)
		{
			choiceSeq.cardinal = DTDCardinal.ZEROMANY;
		} else if (token.type == Scanner.PLUS)
		{
			choiceSeq.cardinal = DTDCardinal.ONEMANY;
		} else
		{
			choiceSeq.cardinal = DTDCardinal.NONE;
		}

		element.content = choiceSeq;
	}

	protected DTDContainer parseChoiceSequence() throws IOException
	{
		TokenType separator = null;

		DTDContainer cs = null;

		for (;;)
		{
			DTDItem item = parseCP();

			Token token = scanner.get();

			if ((token.type == Scanner.PIPE) || (token.type == Scanner.COMMA))
			{
				if ((separator != null) && (separator != token.type))
				{
					throw new IOException();
				}
				separator = token.type;

				if (cs == null)
				{
					cs = new DTDSequence();
				}
				cs.add(item);
			} else if (token.type == Scanner.RPAREN)
			{
				if (cs == null)
				{
					cs = new DTDSequence();
				}
				cs.add(item);
				return cs;
			} else
			{
				throw new IOException();
			}
		}
	}

	protected DTDItem parseCP() throws IOException
	{
		Token token = scanner.get();

		DTDItem item = null;

		if (token.type == Scanner.IDENTIFIER)
		{
			item = new DTDName(token.value);
		} else if (token.type == Scanner.LPAREN)
		{
			item = parseChoiceSequence();
		} else
		{
			throw new IOException();
		}

		item.cardinal = parseCardinality();

		return item;
	}

	protected DTDCardinal parseCardinality() throws IOException
	{
		Token token = scanner.peek();

		if (token.type == Scanner.QUES)
		{
			scanner.get();
			return DTDCardinal.OPTIONAL;
		} else if (token.type == Scanner.ASTERISK)
		{
			scanner.get();
			return DTDCardinal.ZEROMANY;
		} else if (token.type == Scanner.PLUS)
		{
			scanner.get();
			return DTDCardinal.ONEMANY;
		} else
		{
			return DTDCardinal.NONE;
		}
	}

	protected void parseAttlist() throws IOException
	{
		Token token = expect(Scanner.IDENTIFIER);
		DTDElement element = (DTDElement) dtd.elements.get(token.value);
		DTDAttlist attlist = new DTDAttlist(token.value);
		dtd.items.addElement(attlist);
		if (element == null)
		{
			element = new DTDElement(token.value);
			dtd.elements.put(token.value, element);
		}
		token = scanner.peek();

		while (token.type != Scanner.GT)
		{
			parseAttdef(scanner, element, attlist);
			token = scanner.peek();
		}
		expect(Scanner.GT);
	}

	protected void parseAttdef(Scanner scanner, DTDElement element,
			DTDAttlist attlist) throws IOException
	{
		Token token = expect(Scanner.IDENTIFIER);

		DTDAttribute attr = new DTDAttribute(token.value);

		attlist.attributes.addElement(attr);

		element.attributes.put(token.value, attr);

		token = scanner.get();

		if (token.type == Scanner.IDENTIFIER)
		{
			attr.type = token.value;

		} else if (token.type == Scanner.LPAREN)
		{
			attr.type = parseEnumeration();
		}

		token = scanner.peek();

		if (token.type == Scanner.IDENTIFIER)
		{
			scanner.get();
			if (token.value.equals("#FIXED"))
			{
				token = scanner.get();
				attr.defaultValue = token.value;
			} else if (token.value.equals("#REQUIRED"))
			{
			} else if (token.value.equals("#IMPLIED"))
			{
			} else
			{
				throw new IOException();
			}
		} else if (token.type == Scanner.STRING)
		{
			scanner.get();
			attr.defaultValue = token.value;
		}
	}

	protected DTDEnumeration parseEnumeration() throws IOException
	{
		DTDEnumeration enumeration = new DTDEnumeration();

		for (;;)
		{
			Token token = scanner.get();

			if ((token.type != Scanner.IDENTIFIER))
			{
				throw new IOException();
			}

			enumeration.add(token.value);

			token = scanner.peek();

			if (token.type == Scanner.RPAREN)
			{
				scanner.get();
				return enumeration;
			} else if (token.type != Scanner.PIPE)
			{
				throw new IOException();
			}
			scanner.get(); // eat the pipe
		}
	}
}
