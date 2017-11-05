package site;

import java.util.Properties;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

/*
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;
*/

/**
 * Need to set 
 * 
 * environment to set: AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
 * http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
 * 
 * @author Johan Henriksson
 *
 */
public class SendEmailAmazon
	{
  // The name of the Configuration Set to use for this message.
  // If you comment out or remove this variable, you will also need to
  // comment out or remove the header below.
  static final String CONFIGSET = "ConfigSet";
  
  // Amazon SES SMTP host name. This example uses the US West (Oregon) Region.
  static final String HOST = "email-smtp.us-west-2.amazonaws.com";
  static final int PORT = 587;
  
  /**
   * 
   * @param FROM   This must be a SES verified address
   * @param TO     If your account is still in the sandbox, this address must be verified
   * @param SUBJECT
   * @param BODY
   */
  public static void send(String FROM, String TO, String SUBJECT, String BODY) 
  	{
	  String SMTP_USERNAME=System.getenv("AWS_SMTP_USERNAME");
	  String SMTP_PASSWORD=System.getenv("AWS_SMTP_PASSWORD");
	  if(SMTP_PASSWORD==null || SMTP_USERNAME==null)
	  	throw new RuntimeException("Did not find SMTP user/pass info!\n"+System.getenv());

  	try
			{
			// Create a Properties object to contain connection configuration information.
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", PORT); 
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			// Create a Session object to represent a mail session with the specified properties. 
			javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);

			String FROMNAME = "DocuBricks";

			// Create a message with the specified information. 
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM,FROMNAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setContent(BODY,"text/html");

			// Add a configuration set header. Comment or delete the 
			// next line if you are not using a configuration set
			msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try
				{
				System.out.println("Sending...");

				// Connect to Amazon SES using the SMTP username and password you specified above.
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Email sent!");
				}
			catch (Exception ex) 
				{
				System.out.println("The email was not sent.");
				System.out.println("Error message: " + ex.getMessage());
				}
			finally
				{
				// Close and terminate the connection.
				transport.close();
				}
			}
		catch (Exception e)
			{
			System.out.println("Failed to send mail");
			e.printStackTrace();
			}
  	}
	
	
	
	
	
	
	
	




//	private static String KEY_ENV_VARIABLE = "AWS_SECRET_ACCESS_KEY"; // Put your AWS secret access key in this environment variable.
	private static final String MESSAGE = "SendRawEmail"; // Used to generate the HMAC signature. Do not modify.
	private static final byte VERSION =  0x02; // Version number. Do not modify.

	public static void main(String[] args) 
		{

		// Get the AWS secret access key from environment variable AWS_SECRET_ACCESS_KEY.
		String key;// = System.getenv(KEY_ENV_VARIABLE);         	  
		/*
		if (key == null)
			{
			System.out.println("Error: Cannot find environment variable AWS_SECRET_ACCESS_KEY.");  
			System.exit(0);
			}*/

		System.out.print("aws secret access key:");
		Scanner sc=new Scanner(System.in);
		key=sc.nextLine();
		sc.close();
		
		// Create an HMAC-SHA256 key from the raw bytes of the AWS secret access key.
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

		try 
			{         	  
			// Get an HMAC-SHA256 Mac instance and initialize it with the AWS secret access key.
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKey);

			// Compute the HMAC signature on the input data bytes.
			byte[] rawSignature = mac.doFinal(MESSAGE.getBytes());

			// Prepend the version number to the signature.
			byte[] rawSignatureWithVersion = new byte[rawSignature.length + 1];               
			byte[] versionArray = {VERSION};                
			System.arraycopy(versionArray, 0, rawSignatureWithVersion, 0, 1);
			System.arraycopy(rawSignature, 0, rawSignatureWithVersion, 1, rawSignature.length);

			// To get the final SMTP password, convert the HMAC signature to base 64.
			String smtpPassword = DatatypeConverter.printBase64Binary(rawSignatureWithVersion);       
			System.out.println(smtpPassword);
			} 
		catch (Exception ex) 
			{
			System.out.println("Error generating SMTP password: " + ex.getMessage());
			}             
		}
	
	
	}
