package com.seungmoo.java8to11.date_study;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateApi {

    public void runDate() throws InterruptedException {
        /**
         * 이름이 Date 지만 시간 및 timestamp까지 다룰 수 있다. --> 작명이 이상하다.
         * - date에서 getTime()을 한다고???
         *
         * Date 객체는 mutable하다. --> 멀티쓰레드 환경에서 안전하게 쓰기가 어렵다.
         *
         */
        Date date = new Date();
        long time = date.getTime();
        System.out.println(date);

        System.out.println(time);

        // 3초를 sleep하고
        Thread.sleep(1000 * 3);

        Date after3Seconds = new Date();
        // 이거는 3초 지난 시간이 찍힌다.
        System.out.println(after3Seconds);
        // 근데 해당 인스턴스에 3초 전 time 인스턴스를 setting할 수 있다.
        // --> Date는 mutable한 클래스이다. (멀티쓰레드에서 안전하지 못함, thread non safe)
        after3Seconds.setTime(time);
        // 3초 전의 time이 출력된다.
        System.out.println(after3Seconds);

        /**
         * Calendar 에서 MONTH는 0부터 시작한다... --> Type Safe 하지 않다!
         * 7월은 value값이 6이다. --> Calendar.JULY final로 써야 한다.
         *
         * - Calendar 또한 immutable 하지 않다.
         * - Calendar에서도 getTime()가 된다...
         */
        Calendar seungmooBirthDay = new GregorianCalendar(1992, Calendar.JULY, 19);
        seungmooBirthDay.getTime(); // Calendar에서도 getTime을 해버린다... 용도 , 작명의 오류
        seungmooBirthDay.setTime(date); // immutable 하지 않다.

        // 위 뿐만 아니라 그외 여러 불편함으로 인해 joda-Time을 썼다. JSR-310 표준화 스펙을 사용했다.
        /*
        <참고문서>
        •	https://codeblog.jonskeet.uk/2017/04/23/all-about-java-util-date/ --> Date의 불편한 점들을 모아놓은 blog
        •	https://docs.oracle.com/javase/tutorial/datetime/overview/index.html
        •	https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html
         */

        // Java 8에서 제공하는 LocalDate, LocalDateTime --> immutable 하다. (새로운 인스턴스를 만든다.)

    }

}
