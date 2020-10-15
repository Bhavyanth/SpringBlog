node {
   stage('SCM Checkout'){
	url: 'https://github.com/Bhavyanth/SpringBlog' 
   }
 
   stage('Compile-Package'){
    def mvnHome =  tool name: 'Jenkins-Maven', type: 'maven'
	  sh "${mvnHome}/bin/mvn package"
   }
 
   stage('Mvn Package'){
	   sh "${mvnHome}/bin/mvn clean package deploy"
   }
   }
