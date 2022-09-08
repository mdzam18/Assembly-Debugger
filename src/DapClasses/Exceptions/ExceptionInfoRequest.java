package src.DapClasses.Exceptions;

import src.DapClasses.BaseClasses.Request;

public class ExceptionInfoRequest extends Request {

    private ExceptionInfoArguments arguments;

    public ExceptionInfoArguments getArguments() {
        return arguments;
    }

    public void setArguments(ExceptionInfoArguments arguments) {
        this.arguments = arguments;
    }
}
