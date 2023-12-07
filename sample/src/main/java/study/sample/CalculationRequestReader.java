package study.sample;

import java.util.Scanner;

public class CalculationRequestReader {

    public CalculationRequest read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("두 개의 숫자와 연산자를 입력해주세요:");
        String result = scanner.nextLine();
        return new CalculationRequest(result.split(" "));
    }
}
