package my.test.project.netty.chapter7.messagepack;

import org.msgpack.annotation.Message;

/**
 * @Author: qiang.su
 * 这里出现了两个坑，一个是需要在消息类上加上注解Message，另一个就是必须要有默认的无参构造器，不然就会报如下的错误：
 * org.msgpack.template.builder.BuildContext build
 * @Descreption:
 * @Date: Create in  2019/11/29 16:45
 * @Modified by:
 */
@Message
public class UserInfo {
    private String username;
    private String age;
//    private Address addr;
//    @Message
//    class Address{
//        private String address1;
//        private String address2;
//        public Address() {
//        }
//
//        public Address(String address1, String address2) {
//            this.address1 = address1;
//            this.address2 = address2;
//        }
//
//        public String getAddress1() {
//            return address1;
//        }
//
//        public void setAddress1(String address1) {
//            this.address1 = address1;
//        }
//
//        public String getAddress2() {
//            return address2;
//        }
//
//        public void setAddress2(String address2) {
//            this.address2 = address2;
//        }
//    }
//
//    public Address getAddr() {
//        return addr;
//    }
//
//    public void setAddr(Address addr) {
//        this.addr = addr;
//    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public UserInfo() {

    }
}
