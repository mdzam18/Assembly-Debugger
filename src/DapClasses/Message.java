package src.DapClasses;

import java.util.HashMap;

public class Message {
    private int number;
    private String format;
    private HashMap<String, String> variables;
    private boolean sendTelemetry;
    private boolean showUser;
    private String url;
    private String urlLabel;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public HashMap<String, String> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, String> variables) {
        this.variables = variables;
    }

    public boolean isSendTelemetry() {
        return sendTelemetry;
    }

    public void setSendTelemetry(boolean sendTelemetry) {
        this.sendTelemetry = sendTelemetry;
    }

    public boolean isShowUser() {
        return showUser;
    }

    public void setShowUser(boolean showUser) {
        this.showUser = showUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlLabel() {
        return urlLabel;
    }

    public void setUrlLabel(String urlLabel) {
        this.urlLabel = urlLabel;
    }
}
