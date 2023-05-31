package co.edu.uptc.pojos;

public class FileReading {
    private String fileName;
    private byte[] data;
    private int status;

    public FileReading(String fileName, byte[] data, int status) {
        this.fileName = fileName;
        this.data = data;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
