package me.jiwon.inflearnthejavatest.customannotation;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// 커스텀하게 어노테이션을 가질 수 있음
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Test
@Tag("slow")
public @interface SlowTest {
}
