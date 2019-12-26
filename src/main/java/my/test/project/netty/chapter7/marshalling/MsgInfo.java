package my.test.project.netty.chapter7.marshalling;

import java.io.Serializable;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/12/10 14:44
 * @Modified by:
 */
public class MsgInfo  implements Serializable {
    private byte header;
    private String body;
    private long length;
    private byte type;
    private User user;

    class User implements  Serializable{
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
