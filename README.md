

# A simplified Discord application written in Java RMI 

The goal of the project is to reproduce in pure **JAVA RMI**, a quite similar behaviour of the Discord application we are using during this confinement period. This will allow the students to apply the various RMI concepts.

Moreover, even if the rest of the course pertains to the study of the Message Oriented Middleware (MOM) Apache Active MQ exposing the Java Messaging System API, that technology will not be used in the project. However, some functionalities that your Discord application will feature are going to be close to what a MOM should implement in order to offer 1) publish/subscribe on a topic, and 2) send/receive on a message queue features. 

The server keeps the credentials and needed information about all the registered users, so to allow to propagate any message it should. All communications among users have to pass through the server. This is a point of failure and can harm scalability of the system. However, it is like this that the Discord (and a lot of other communication-oriented) application works. Here follow the (sometimes simplified) capabilities you must consider supporting. 



 

- Ability to write a textual message and read all those exchanged in the textual channel/saloon, including those that arose when the user was disconnected from the system. 

- Ability to know who the registered users are, in order to be able to privately communicate with a given user through private text messages, but in an asynchronous manner.
