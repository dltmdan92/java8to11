package com.seungmoo.java8to11.date_study;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateApi2 {

    public void runDateApi() {

        // 지금 이 순간을 "기계 시간"으로 표현하는 방법
        // Instant.now(): 현재 UTC (GMT)를 리턴한다.
        Instant instant = Instant.now();
        //2021-01-01T11:54:54.398729500Z
        System.out.println(instant); // 기준시 UTC = GMT (Universal Time Coordinated == Greenwich Mean Time)
        System.out.println(instant.atZone(ZoneId.of("UTC"))); // 위와 동일

        ZoneId zone = ZoneId.systemDefault();
        // Asia/Seoul
        System.out.println(zone);
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        // 2021-01-01T20:54:54.398729500+09:00[Asia/Seoul]
        System.out.println(zonedDateTime);

        /*
        "인류용 일시"를 표현하는 방법
        •	LocalDateTime.now(): 현재 시스템 Zone에 해당하는(로컬) 일시를 리턴한다.
        •	LocalDateTime.of(int, Month, int, int, int, int): 로컬의 특정 일시를 리턴한다.
        •	ZonedDateTime.of(int, Month, int, int, int, int, ZoneId): 특정 Zone의 특정 일시를 리턴한다.
         */
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime birthDay = LocalDateTime.of(1992, Month.JULY, 19, 0, 0, 0);
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(nowInKorea);

        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTime1 = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println(zonedDateTime1);
        // Instant <--> LocalDateTime : 이렇게 서로 Convert 할 수 있다.
        System.out.println(zonedDateTime1.toLocalDateTime());

        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthday = LocalDate.of(Year.now(ZoneId.of("Asia/Seoul")).getValue(), Month.JULY, 19);
        System.out.println("thisYearBirthday : " + thisYearBirthday);

        Period period = Period.between(today, thisYearBirthday);
        System.out.println(period.getDays());
        // 위의 period랑 동일하다.
        Period until = today.until(thisYearBirthday);
        // 일 수 차이 구하기 (위의 period.getDays()랑 동일)
        System.out.println(until.get(ChronoUnit.DAYS));
    }

    public void runDuration() {
        // Period는 휴먼용, Duration은 머신용
        Instant now = Instant.now();
        Instant plus = now.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(now, plus);
        System.out.println(between.getSeconds());
    }

    public void runLocalDateTime() {
        Date date = new Date();
        // Date를 Instant로 바꿀 수 있다.
        Instant instant = date.toInstant();
        // Instant를 Date로 바꿀 수 있다.
        Date newDate = Date.from(instant);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        // GregorianCalendar를 LocalDateTime으로 바꾸는 법
        LocalDateTime dateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId();
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now));

        // LocalDate의 API는 새로운 인스턴스를 return 한다.
        LocalDateTime plusedNow = now.plus(10, ChronoUnit.DAYS);

        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("formatting : " + now.format(MMddyyyy));

        LocalDate parse = LocalDate.parse("07/19/1992", MMddyyyy);
        System.out.println("parsing : " + parse);
    }

}
