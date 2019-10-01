package task.money.transfer.domain;

public enum ResponseMessage {
    SUCCESS ("Success"),
    ERROR ("Error");

    private String response;
    
    ResponseMessage(String response) {
        this.response = response;
    }

    public String getStatus() {
        return response;
    }

    public void setStatus(String response) {
        this.response = response;
    }
}