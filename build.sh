if test -f "$classes/four"; then
    mkdir classes/
fi
if [[ $1 = "3" ]]
then
    javac -cp lib/jade.jar src/com/example/Main.java src/com/challenge/three/*.java -d classes/
elif [[ $1 = "4" ]]
then
    javac src/com/challenge/four/*.java -d classes/
else
    echo "Pass number of challenge to build"
fi
