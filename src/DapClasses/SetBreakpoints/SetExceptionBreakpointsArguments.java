package src.DapClasses.SetBreakpoints;

import src.DapClasses.Exceptions.ExceptionFilterOptions;
import src.DapClasses.Exceptions.ExceptionOptions;

public class SetExceptionBreakpointsArguments {
    private String[] filters;

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public ExceptionFilterOptions[] getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(ExceptionFilterOptions[] filterOptions) {
        this.filterOptions = filterOptions;
    }

    public ExceptionOptions[] getExceptionOptions() {
        return exceptionOptions;
    }

    public void setExceptionOptions(ExceptionOptions[] exceptionOptions) {
        this.exceptionOptions = exceptionOptions;
    }

    private ExceptionFilterOptions[] filterOptions;
    private ExceptionOptions[] exceptionOptions;
}
