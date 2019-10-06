package com.gagan.school.roles;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public enum ROLE {
    SUPER_ADMIN(1),
    ADMIN(2),
    TEACHER(3),
    STUDENT(4),
    DEFAULT(5);

    private   final int number;
    ROLE(int num){
        number=num;
    }

    public int getNumber() {
        return number;
    }}
