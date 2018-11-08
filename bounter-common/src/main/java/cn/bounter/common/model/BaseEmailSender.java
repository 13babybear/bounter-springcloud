package cn.bounter.common.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具
 * 子类只需要继承该类并配置发送邮件账号就可以发送各种邮件
 * @author simon
 *
 */
public class BaseEmailSender {

	private static final Logger logger = LoggerFactory.getLogger(BaseEmailSender.class);

	@Autowired
    private JavaMailSender javaMailSender;


	/**
	 * 群体发送简单的文本邮件
	 * @param sendFrom
	 * @param sendTo
	 * @param subject
	 * @param textcontent
	 */
	public void sendSimpleMessage(String sendFrom, String[] sendTo, String subject, String textcontent) {
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(sendFrom);
			mail.setTo(sendTo);
			mail.setSubject(subject);
			mail.setText(textcontent);
			//发送
			javaMailSender.send(mail);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}


	/**
	 * 单个发送简单文本邮件
	 * @param sendFrom
	 * @param sendTo
	 * @param subject
	 * @param textcontent
	 */
	public void sendSimpleMessage(String sendFrom, String sendTo, String subject, String textcontent) {
		sendSimpleMessage(sendFrom, new String[]{sendTo}, subject, textcontent);
	}


	/**
	 * 群体发送HTML内容格式的邮件
	 * @param sendFrom
	 * @param sendTo			收件人组
	 * @param subject			主题
	 * @param htmlContent		HTML内容
	 */
	public void sendHtmlMessage(String sendFrom, String[] sendTo, String subject, String htmlContent) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom(sendFrom);
			mimeMessageHelper.setTo(sendTo);
			mimeMessageHelper.setSubject(subject);
			// true 表示启动HTML格式的邮件
			mimeMessageHelper.setText(htmlContent, true);

			// 发送邮件
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}


	/**
	 * 单个发送HTML内容格式的邮件
	 * @param sendFrom
	 * @param sendTo			单个收件人
	 * @param subject			主题
	 * @param htmlContent		HTML内容
	 * @throws Exception
	 */
	public void sendHtmlMessage(String sendFrom, String sendTo, String subject, String htmlContent) {
		sendHtmlMessage(sendFrom, new String[]{sendTo}, subject, htmlContent);
	}
}
