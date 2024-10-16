all:
	javac -d ../lib ../src/MainTemplateFrame.java
	javac -d ../lib ../src/SearchFrame.java
	javac -cp ../lib -d ../lib ../src/MainMenuTemplateFrame.java
	javac -cp ../lib -d ../lib ../src/LineComponentsTemplateFrame.java
	javac -cp ../sourcelibs -d ../lib ../src/FileManager.java
	javac -cp ../sourcelibs:../lib -d ../lib ../src/TemplateManager.java

	javac -cp ../sourcelibs:../lib -d . ../src/Main.java
	

test:
	javac -cp ../lib -d ../target ../test/*

run:
	java -cp .:../sourcelibs:../lib Main

runTest:
	# Add Unit Testing: java -cp ../target:../lib [TestName]

doc:
	javadoc -cp ../lib:../sourcelibs -d ../doc ../src/MainTemplateFrame.java ../src/SearchFrame.java ../src/MainMenuTemplateFrame.java \
	../src/LineComponentsTemplateFrame.java ../src/FileManager.java

clean:
	rm -f *.class
	rm -rf ../doc/*
	rm -rf ../lib/*
	rm -rf ../target/*
