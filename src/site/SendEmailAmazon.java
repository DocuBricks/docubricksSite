package site;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

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
	}