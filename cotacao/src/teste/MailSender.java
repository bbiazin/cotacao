package teste;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Contato;
import modelo.Habilitacao;
import modelo.Usuario;

public class MailSender {

	@Resource(mappedName="java:jboss/mail/Default")
	private Session mailSession;

	public void sendMail() throws MessagingException {
		MimeMessage m = new MimeMessage(mailSession);
		Address from = new InternetAddress("rogeriodometerco@gmail.com");
		Address[] to = new InternetAddress[] { new InternetAddress(
				"rogeriodometerco@hotmail.com") };
		m.setFrom(from);
		m.setRecipients(Message.RecipientType.TO, to);
		m.setSubject("Google JavaMail Test");
		m.setContent("Test from inside JBoss AS7 Server", "text/plain");
		Transport.send(m);
		System.out.println("Mail Sent Successfully.");
	}

	public void sendMail2() throws Exception {
		MimeMessage message = new MimeMessage( mailSession);
		message.setFrom( new InternetAddress( "rogeriodometerco@gmail.com" ) );
		//message.setReplyTo( new Address[]{new InternetAddress( mailFrom )} );
		message.addRecipient( Message.RecipientType.TO, new InternetAddress("rogeriodometerco@hotmail.com" ) );
		//message.setHeader( "Content-Type", this.mailContentType + "; charset=\"" + this.mailEncoding + "\"");
		message.setSubject("subject");
		message.setText("texto do email");
		Transport.send( message );
		System.out.println("Sent message successfully....");
	}


	public void sendMail3() throws Exception {
		Properties properties = System.getProperties();		
		properties.put("mail.smtp.starttls.enable", "true"); 
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.user", "rogeriodometerco@gmail.com"); // User name
		properties.put("mail.smtp.password", "roDDas3roDDas3"); // password
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");


		//Session session = Session.getDefaultInstance(properties);
		Session session = Session.getDefaultInstance(properties,
				new Authenticator() {
			protected PasswordAuthentication  getPasswordAuthentication() {
				return new PasswordAuthentication(
						"rogeriodometerco@gmail.com", "roDDas3roDDas3");
			}
		});
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("rogeriodometerco@gmail.com"));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress("rogeriodometerco@hotmail.com"));
		message.setSubject("This is the Subject Line!");
		message.setText("This is actual message");
		Transport.send(message);
		System.out.println("Sent message successfully....");
	}


	public void sendMail4() throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		// message subject
		message.setSubject("Java Programming Forums");
		// message body
		message.setContent("Hey! Visit http://www.JavaProgrammingForums.com", "text/plain");

		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress("rogeriodometerco@hotmail.com"));

		transport.connect
		("smtp.gmail.com", 465, "rogeriodometerco@gmail.com", "roDDas3roDDas3");

		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	public void sendMail5(Usuario remetente, List<Habilitacao> habilitacoes) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		// message subject
		message.setSubject("[Em teste] Nova cotação de " + remetente.getNome());
		// message body
		message.setContent("Você recebeu nova cotação em http://itinerario.no-ip.org:9055/carregamento/paginas/destinatario/cotacoesAResponder.xhtml. Isso é um teste.", "text/plain");
		boolean enviar = false;
		for (Habilitacao h: habilitacoes) {
			if (h.getContato().getEmail().contains("rogerio") 
					|| h.getContato().getEmail().contains("pereira") 
					|| h.getContato().getEmail().contains("pavez") 
					|| h.getContato().getEmail().contains("martins")) {

				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(h.getContato().getEmail()));
				enviar = true;
			}

		}
		if (enviar) {

			transport.connect
			("smtp.gmail.com", 465, "rogeriodometerco@gmail.com", "roDDas3roDDas3");

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
		}
	}

	public void notificarCotacao(Usuario remetente, List<Contato> destinatarios) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		// message subject
		message.setSubject("[Isso é um teste] Nova cotação de " + remetente.getNome());
		// message body
		message.setContent("Você recebeu nova cotação em http://itinerario.no-ip.org:9055/carregamento/paginas/destinatario/cotacoesAResponder.xhtml. Isso é um teste.", "text/plain");
		boolean enviar = false;
		for (Contato c: destinatarios) {
			if (c.getEmail().contains("rogerio") 
					|| c.getEmail().contains("pereira") 
					|| c.getEmail().contains("pavez") 
					|| c.getEmail().contains("coamo") 
					|| c.getEmail().contains("martins")) {

				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(c.getEmail()));
				enviar = true;
			}

		}
		if (enviar) {

			transport.connect
			("smtp.gmail.com", 465, "rogeriodometerco@gmail.com", "roDDas3roDDas3");

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
		}
	}

	public void sendMail6(String email, String senha) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		// message subject
		message.setSubject("[Em teste] Acesso liberado");
		// message body
		message.setContent("Acesse http://itinerario.no-ip.org:9055/carregamento. "
				+ "\n E-mail: " + email
				+ "\n Senha " + senha, "text/plain");
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(email));

		transport.connect
		("smtp.gmail.com", 465, "rogeriodometerco@gmail.com", "roDDas3roDDas3");

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	public void sendMail7(Usuario usuario) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		// message subject
		message.setSubject("[Em teste] Senha esquecida");
		// message body
		message.setContent("Acesse http://itinerario.no-ip.org:9055/carregamento com a senha "
				+ usuario.getSenha(), "text/plain");
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(usuario.getEmail()));

		transport.connect
		("smtp.gmail.com", 465, "rogeriodometerco@gmail.com", "roDDas3roDDas3");

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

}