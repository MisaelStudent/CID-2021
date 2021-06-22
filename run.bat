@echo off
if "%1" == "three" (
    java -cp lib/jade.jar;classes/ jade.Boot -gui misael:com.challenge.three.BookSellerAgent
) 
rem elif [[ $1 = "4" ]]; then
rem    java -cp classes/ com.challenge.four.Main $2
rem elif [[ $1 = "5" ]]; then
rem    java -cp lib/jade.jar:classes/ jade.Boot -gui misael:com.challenge.five.SimpleLinearRegressionAgent
rem else
rem    echo "Pass at least 1 argument, number of challenge"
rem fi

