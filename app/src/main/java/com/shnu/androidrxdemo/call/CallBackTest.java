package com.shnu.androidrxdemo.call;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/09
 * @description： （回调测试！！）
 */
public class CallBackTest {

    public static void main(String[] args) {
        System.out.println("------ Telephone Sample 03-----");
        Telephone telephone = new Telephone(new Answer() {
            @Override
            public void say(String fromPerson) {
                System.out.println("Hello " + fromPerson + "!!");
            }
        });
        telephone.bell("Daniel");
        telephone.bell("Jack");
        telephone.bell("Pony");
    }

    public static class Telephone {

        private Answer answer;

        public Telephone(Answer answer) {
            this.answer = answer;
        }

        public void bell(String fromPerson) {
            System.out.println("bli-bli-bli......");
            System.out.println("bli-bli-bli......");
            answer.say(fromPerson);
        }
    }

    public interface Answer {

        /**
         * 接听电话
         *
         * @param fromPerson 来电话的人
         */
        void say(String fromPerson);
    }
}
