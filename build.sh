if test -f "$classes/four"; then
    mkdir classes/
fi
if [[ $1 = "3" ]]
then
    javac -cp lib/jade.jar src/com/example/Main.java src/com/challenge/three/*.java -d classes/
elif [[ $1 = "4" ]]
then
    javac src/com/challenge/four/*.java src/com/util/math/*.java -d classes/
elif [[ $1 = "5" ]]
then
    javac -cp lib/jade.jar src/com/challenge/five/*.java src/com/util/math/*.java -d classes/
else
    echo "Pass number of challenge to build"
fi
