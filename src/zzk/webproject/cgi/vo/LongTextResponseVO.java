package zzk.webproject.cgi.vo;

import java.util.Objects;

public class LongTextResponseVO {
    private String method;
    private String result;
    private String content;

    public LongTextResponseVO() {
    }

    public LongTextResponseVO(String method, String result, String content) {
        this.method = method;
        this.result = result;
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongTextResponseVO)) return false;
        LongTextResponseVO that = (LongTextResponseVO) o;
        return Objects.equals(getMethod(), that.getMethod()) &&
                Objects.equals(getResult(), that.getResult()) &&
                Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getResult(), getContent());
    }

    @Override
    public String toString() {
        return "LongTextResponseVO{" +
                "method='" + method + '\'' +
                ", result='" + result + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
