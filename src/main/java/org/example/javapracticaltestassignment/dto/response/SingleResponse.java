package org.example.javapracticaltestassignment.dto.response;

public class SingleResponse<T> {

    private T data;

    public SingleResponse(T data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}