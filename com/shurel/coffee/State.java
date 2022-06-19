package com.shurel.coffee;
//stores the state of the machine
enum State {
    ACTION("choosing an ACTION"), COFFEE_CHOICE("choosing a variant of coffee"), FILL_WATER("fill water"),
        FILL_MILK("fill milk"), FILL_COFFEE("fill coffee"), FILL_CUP("fill cup");
    private String name;
    State(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}