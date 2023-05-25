package service;

import model.Response;

import java.io.IOException;

public interface Service {

    Response serve() throws IOException;
}
