gitaddall:
	git add src WebContent

upload:
#	scp /home/mahogny/eclipse/docubricksSite.war ubuntu@ec2-52-37-67-196.us-west-2.compute.amazonaws.com:/home/ubuntu/apache-tomcat-7.0.68/webapps/ROOT.war
	scp /home/mahogny/eclipse/docubricksSite.war ubuntu@docubricks.org:/home/ubuntu/apache-tomcat-7.0.68/webapps/ROOT.war
	#todo: an ant file to build the war file instead
ssh:
	ssh ubuntu@ec2-52-37-67-196.us-west-2.compute.amazonaws.com

