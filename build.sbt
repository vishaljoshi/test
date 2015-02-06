name := "test"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)  


libraryDependencies in Global ++= Seq(
  cache,
  javaJdbc,
  javaEbean,
  "org.springframework" % "spring-context" % "3.2.6.RELEASE",
  "org.springframework" % "spring-aop" % "3.2.6.RELEASE",  
  "com.fasterxml.jackson.core" % "jackson-core" % "2.4.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.0",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.0",
  "org.mongodb" % "mongo-java-driver" % "2.13.0",
  "org.springframework.data" % "spring-data-mongodb" % "1.6.2.RELEASE"
  )   

play.Project.playJavaSettings
