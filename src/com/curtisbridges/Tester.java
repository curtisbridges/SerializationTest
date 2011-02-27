package com.curtisbridges;

import java.io.Serializable;

public class Tester implements Serializable {
    private Object data;

    public Tester(Object obj) {
        data = obj;
    }

    public Object getData() {
        return data;
    }

    public String toString() {
        return "[Tester]" + data.toString();
    }
}
