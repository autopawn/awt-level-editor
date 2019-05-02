run: compile
	java leveled.LevelEditor
jar: compile
	jar -cvfm leveled.jar Manifest.txt leveled/*.class
compile:
	rm leveled/*.class || true
	javac leveled/*.java
