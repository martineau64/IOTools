all:
	javac -d ../lib ../src/TemplateFrame.java
	javac -d ../lib ../src/SearchFrame.java
	javac -cp ../sourcelibs -d ../lib ../src/FileManager.java
	javac -cp ../sourcelibs:../lib -d . ../src/Main.java
	

test:
	javac -cp ../lib -d ../target ../test/*

run:
	java -cp .:../sourcelibs:../lib Main

runTest:
	# Add Unit Testing: java -cp ../target:../lib [TestName]

doc:
	javadoc -d ../doc ../src/*

clean:
	rm -f *.class
	rm -f -R ../doc/*
	rm -f -R ../lib/*
	rm -f -R ../target/*
