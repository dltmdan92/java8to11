package com.seungmoo.java8to11.optional_study;

import java.util.Optional;

public class OnlineClass {
    private Integer id;
    private String title;
    private boolean closed;
    private Progress progress;

    public OnlineClass(Integer id, String title, boolean closed) {
        this.id = id;
        this.title = title;
        this.closed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * 리턴 되는 것이 Null 일 수 있는 경우 Optional 타입으로 리턴하라
     * 리턴타입은 Optional 타입으로 할 것.
     *
     * 파라미터 타입으로 Optional을 쓰는 것은 권장하지 않는다.
     *
     * Optional.ofNullable을 리턴하면 Null일 수도 있다(ofNullable)는 것.
     * Optional.of --> Null이 아니라는 것임 (NullPointerException 발생할 우려 있음)
     * @return
     * 리턴할 때 절대로 return null; Null을 리턴하는 일은 없어야 한다.
     * --> 정 리턴할게 없다하면 Optional.empty()라도 리턴해라.
     *
     * Optional<Optional<>> --> 이런식으로 Optional에 Optional로 감싸지 말 것.
     */
    public Optional<Progress> getProgress() {
        /*
        if (this.progress == null) {
            throw new IllegalStateException();

            //이렇게 하면 문제점
            //1. check Exception을 뱉을 경우 에러 처리를 강제하게 된다.
            //2. Error에 대한 stackTrace를 다 리턴하게 된다 --> 리소스 낭비
        }
        */
        return Optional.ofNullable(progress);
    }

    /**
     * Optional을 파라미터로 받는 것은 안좋다.
     * setProcess(null) -->  일부러 이렇게 주게 될 수도 있음.
     * @param progress
     */
    /*
    public void setProgress(Optional<Progress> progress) {
        // 파라미터가 null로 들어오게 될 경우, Optional로 받아도 또 null 체크 해야 함
        if (progress != null) { // progress.isPresent() --> 이거 안됨!! null이면 에러 발생!
            progress.ifPresent(prog -> this.progress = prog);
        }
    }
    */

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
