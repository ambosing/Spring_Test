package study.sample;

public class InvalidOperatorException extends RuntimeException{

    public InvalidOperatorException() {
        super("Invaild operator, you need to choose one of (+,-,*,/)");
    }
}
