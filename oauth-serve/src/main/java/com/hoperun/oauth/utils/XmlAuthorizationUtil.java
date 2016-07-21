package com.hoperun.oauth.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hoperun.oauth.entity.po.Authorization;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;

public class XmlAuthorizationUtil {

	public static Document readXmlUnderClasspath(String file) throws DocumentException {
		InputStream in = XmlAuthorizationUtil.class.getClassLoader().getResourceAsStream(file);
		SAXReader reader = new SAXReader();
		return reader.read(in);
	}

	public static Document readXmlUnderFs(String filePath) throws DocumentException, FileNotFoundException {
		File file = new File(filePath);
		InputStream in = new FileInputStream(file);
		SAXReader reader = new SAXReader();
		return reader.read(in);
	}

	public static Document readXmlFile(File file) throws DocumentException, FileNotFoundException {
		InputStream in = new FileInputStream(file);
		SAXReader reader = new SAXReader();
		return reader.read(in);
	}

	public static AuthorizationRequierment xml2Bean(File file) {
		if (file == null)
			return null;
		AuthorizationRequierment ar = null;
		try {
			ar = new AuthorizationRequierment();
			
			Set<Authorization> authorizationsToResource = new HashSet<Authorization>();
			Set<Authorization> authorizationsToUser = new HashSet<Authorization>();
			ar.setAuthorizationsToResource(authorizationsToResource);
			ar.setAuthorizationsToUser(authorizationsToUser);
			
			Document document = readXmlFile(file);
			Element root = document.getRootElement();
			ar.setName(root.attribute("name").getText());
			Element in = root.element("in");
			parseToAuth(true,in,authorizationsToUser,ar);
			Element out = root.element("out");
			parseToAuth(false,out,authorizationsToResource,ar);
			ar.setVersion(1.0D);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * @param in
	 * @param authorizationsToUser
	 * @param ar 
	 */
	private static void parseToAuth(boolean isIn,Element in, Set<Authorization> authorizationsToUser, AuthorizationRequierment ar) {
		List<Element> inres = in.elements("resource");
		for(Element res:inres  ){
			Authorization auth = new Authorization();
			auth.setResource(res.attribute("resource").getText());
			auth.setCode(res.attribute("code").getText());
			auth.setOperation(Integer.parseInt(res.attribute("operation").getText()));
			auth.setRequired(Boolean.parseBoolean(res.attribute("isRequired").getText()));
			if(isIn)
				auth.setArToUser(ar);
			else
				auth.setArToRes(ar);
			authorizationsToUser.add(auth);
			auth.setDate(System.currentTimeMillis());
		}
	}

	
	
}
