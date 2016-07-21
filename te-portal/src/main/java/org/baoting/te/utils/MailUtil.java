/*****************************************************************************
 *
 *                      HOPERUN PROPRIETARY INFORMATION
 *
 *          The information contained herein is proprietary to HopeRun
 *           and shall not be reproduced or disclosed in whole or in part
 *                    or used for any design or manufacture
 *              without direct written authorization from HopeRun.
 *
 *            Copyright (coffee) 2015 by HopeRun.  All rights reserved.
 *
 *****************************************************************************/
package org.baoting.te.utils;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassName: MailUtil
 * @description
 * @author xu_hongli
 * @Date   Nov 24, 2015
 * 
 */
public class MailUtil {

	private static final Log logger = LogFactory.getLog(MailUtil.class);
	
	private static String host;
	private static String from;
	private static String pass;
	private static String port;
	private static String trans;
	private static String bccTo;
	
	static {
		host = SystemConfig.getProperty("mail.protocol");
		from = SystemConfig.getProperty("mail.from");
		pass = SystemConfig.getProperty("mail.password");
		port = SystemConfig.getProperty("mail.port");
		trans = SystemConfig.getProperty("mail.transport");
		bccTo = SystemConfig.getProperty("mail.bcc.list");
	}
	
	public static boolean sendMailWithAttachment(String mailTo, String mailTitle, String mailContent, File[] attachments){
		logger.info("Enter MailUtil's sendMailWithAttachment method. the param is emailTo: "+mailTo+
				",mailTitle: "+mailTitle+",mailContent: "+mailContent+",attachment: "+attachments);
		Transport transport = null;
		
		//store the info of mail-system
		Properties props = System.getProperties();
		props.put("mail.stmp.starttls.enable", "true");
		props.put("mail.stmp.host", host);
		props.put("mail.stmp.user", from);
		props.put("mail.stmp.password", pass);
		props.put("mail.stmp.port", port);
		props.put("mail.stmp.auth", "true");
		logger.debug("host="+host +",from=" +from+",pass="+pass +",port="+port);
		
		//create a new session according to the email-attribute
		MyAuthenticator auth = new MyAuthenticator(from,pass);
		Session session = Session.getDefaultInstance(props,auth);
		
		session.setDebug(true);
		
		MimeMessage message = new MimeMessage(session);
		
		Multipart mp = new MimeMultipart();
		
		try {
			message.setFrom(new InternetAddress(from));
			
			InternetAddress toAddress = new InternetAddress(mailTo);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			
			InternetAddress bccList = new InternetAddress(bccTo);
			message.addRecipient(Message.RecipientType.BCC, bccList);
			
			message.setSubject(mailTitle);
			
			//mail content part 
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(mailContent, "text/html;charset=UTF-8");
			mp.addBodyPart(contentPart);
			
			//mail attachment part
			if(attachments != null && attachments.length > 0){
				for(int i = 0; i < attachments.length; i++){
					File attachment = attachments[i];
					if(attachment != null){
						BodyPart attachmentPart = new MimeBodyPart();
						DataSource source = new FileDataSource(attachment);
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
						mp.addBodyPart(attachmentPart);
					}
				}
			}
			message.setContent(mp);
			//save email
			message.saveChanges();
			
			transport = session.getTransport(trans);
			
			//validate user and password 
			transport.connect(host, from, pass);
			logger.info("Succeed in connecting, begin to send main.");
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("Succeed send email");
			transport.close();
			return true;
		} catch (Exception e) {
			logger.error("Fail to send mail : "+e.getMessage());
			return false;
		}
	}
}

class MyAuthenticator extends Authenticator {
	String name;
	String password;
	
	public MyAuthenticator(String name, String password){
		this.name = name;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(name,password);
	}
}
