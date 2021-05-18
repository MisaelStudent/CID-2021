if [[ $1 = "3" ]]; then
    java -cp lib/jade.jar:classes/ jade.Boot -gui $2
elif [[ $1 = "4" ]]; then
    java -cp classes/ com.challenge.four.Main $2
else
    echo "Pass at least 1 argument, number of challenge"
fi
