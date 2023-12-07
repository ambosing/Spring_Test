package study.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

public class SampleApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SampleApplication.class, args);
		CalculationRequestReader calculationRequestReader = new CalculationRequestReader();
		CalculationRequest calculationRequest = calculationRequestReader.read();
		long answer = new Calculator().calculate(
				calculationRequest.getNum1(),
				calculationRequest.getOperator(),
				calculationRequest.getNum2());
		System.out.println(answer);
	}

}
