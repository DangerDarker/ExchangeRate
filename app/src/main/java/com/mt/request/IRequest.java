package com.mt.request;

public abstract class IRequest  {
    protected  final HttpRequester request;
    public IRequest(){
        request = new HttpRequester();
    }
    protected  abstract  String getResultURL();
}
