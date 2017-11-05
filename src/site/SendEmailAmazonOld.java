package site;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Need to set 
 * 
 * environment to set: AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
 * http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
 * 
 * @author Johan Henriksson
 *
 */
public class SendEmailAmazonOld
	{
	
	@SuppressWarnings("deprecation")
	public static void send(String FROM, String TO, String SUBJECT, String BODY) 
		{
		System.out.println("Email-----------------");
		System.out.println("FROM: "+FROM);
		System.out.println("TO: "+TO);
		System.out.println("SUBJECT: "+SUBJECT);
		System.out.println("BODY: "+BODY);
		System.out.println("----------------------");
		
		// Construct an object to contain the recipient address.
		Destination destination = new Destination().withToAddresses(new String[]{TO});

		// Create the subject and body of the message.
		Content subject = new Content().withData(SUBJECT);
		Content textBody = new Content().withData(BODY); 
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

		try
			{        
			/*
			AmazonSimpleEmailServiceClient client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_WEST_2)
          .build();
          */
			
			// Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
			// Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
			// using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
			// AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
			// For more information, see http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();

			// Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
			// status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
			// region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
			// the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
			// and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
			Region REGION = Region.getRegion(Regions.US_WEST_2);
			client.setRegion(REGION);

			// Send the email.
			client.sendEmail(request);  
			System.out.println("Email sent!");
			}
		catch (Exception ex) 
			{
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
			}
		}
	
	
	
	
	
	




	private static final String KEY_ENV_VARIABLE = "AWS_SECRET_ACCESS_KEY"; // Put your AWS secret access key in this environment variable.
	private static final String MESSAGE = "SendRawEmail"; // Used to generate the HMAC signature. Do not modify.
	private static final byte VERSION =  0x02; // Version number. Do not modify.

	public static void main(String[] args) 
		{

		// Get the AWS secret access key from environment variable AWS_SECRET_ACCESS_KEY.
		String key = System.getenv(KEY_ENV_VARIABLE);         	  
		if (key == null)
			{
			System.out.println("Error: Cannot find environment variable AWS_SECRET_ACCESS_KEY.");  
			System.exit(0);
			}

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
